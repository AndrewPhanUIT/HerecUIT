package uit.herec.hyperledger.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import uit.herec.common.dto.ChaincodeScript;
import uit.herec.hyperledger.Cmd;

@Service
public class CmdImpl implements Cmd {
    private final static Logger logger = LoggerFactory.getLogger(CmdImpl.class);
    private final static String ROOT_PATH = System.getProperty("user.dir");

    @Value("${hyperledger.folder-name}")
    private String fabricFolderName;

    @Override
    public void startServer() {
        logger.info("---- START HYPERLEDGER FABRIC NETWORK");
        Path path = Paths.get(ROOT_PATH);
        try {
            this.exec(path, "./startFabric.sh", ROOT_PATH);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopServer() {
        logger.info("----STOP FABRIC NETWORK----");
        Path path = Paths.get(ROOT_PATH, this.fabricFolderName);
        try {
            this.exec(path, "./herec.sh", "down");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void exec(Path dir, String... cmdArgs) throws IOException, InterruptedException {
        String cmdString = String.join(" ", cmdArgs);
        logger.info("exec - cmdString: " + cmdString);
        File dirFile = dir != null ? dir.toFile() : null;
        Process process = Runtime.getRuntime().exec(cmdArgs, null, dirFile);
        int exitCode = process.waitFor();
        InputStream errorStream = process.getErrorStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
            for (String line; (line = reader.readLine()) != null;) {
                logger.error("exec - " + line);
            }
        }
        if (exitCode != 0)
            logger.error("Cant exec with cmdArgs: " + cmdString + " - dir: " + dir);
        else
            logger.info("Started fabric network");
    }

    private void exec(String... cmdArgs) throws IOException, InterruptedException {
        exec(null, cmdArgs);
    }

    @Override
    public boolean invokeChaincode(String orgName, String peerName, String peerPort, String channel, String chaincode,
            ChaincodeScript script, List<String> orgs, List<String> ports, List<String> peers) {
        Gson gson = new Gson();
        logger.info("START INVOKING CHAINCODE");
        Path path = Paths.get(ROOT_PATH, this.fabricFolderName);
        if (orgs.size() != ports.size() || orgs.size() != peers.size() || ports.size() != peers.size()) {
            logger.error("Something wrong with peer in channel");
            return false;
        }
        if (script == null) {
            logger.error("Chaincode Script cannot be null");
            return false;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < orgs.size(); i++) {
            builder.append("--peerAddresses ").append(
                    String.format("%s.%s.herec.uit:%s ", peers.get(i), orgs.get(i).toLowerCase(), ports.get(i)));
        }
        for (int i = 0; i < orgs.size(); i++) {
            builder.append("--tlsRootCertFiles ").append(String.format(
                    "/opt/gopath/src/HerecUIT/hyperledger/fabric/peer/crypto/peerOrganizations/%s.herec.uit/peers/%s.%s.herec.uit/tls/ca.crt ",
                    orgs.get(i).toLowerCase(), peers.get(i), orgs.get(i).toLowerCase()));
        }

        try {
            this.exec(path, "./invokeChaincode.sh", String.format("%sMSP", orgName), peerName, peerPort,
                    orgName.toLowerCase(), channel, chaincode, gson.toJson(script), builder.toString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        logger.info("END INVOKING CHAINCODE");
        return true;
    }

    public static void main(String[] args) {
        String orgName = "Client";
        String peerName = "peer0";
        String peerPort = "7051";
        String channel = "herecchannel";
        String chaincode = "diagnosis";
        ChaincodeScript script = new ChaincodeScript("initLedger", new ArrayList<>());
        String[] orgs = {"Client", "Quan12"};
        String[] peers = {"peer0", "peer0"};
        String[] ports = {"7051", "9051"};
        new CmdImpl().invokeChaincode(orgName, peerName, peerPort, channel, chaincode, script, Arrays.asList(orgs), Arrays.asList(ports), Arrays.asList(peers));
    }
}
