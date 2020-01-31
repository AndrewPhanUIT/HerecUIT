package uit.herec.hyperledger.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import uit.herec.hyperledger.Cmd;

@Service
public class CmdImpl implements Cmd {
    private final static Logger logger = LoggerFactory.getLogger(CmdImpl.class);
    
    @Override
    public void startServer() {
        logger.info("---- START HYPERLEDGER FABRIC NETWORK");
        String rootSystem = System.getProperty("user.dir");
        Path path = Paths.get(rootSystem);
        try {
            this.exec(path, "./startFabric.sh", rootSystem);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("---- END  HYPERLEDGER FABRIC NETWORK");
    }

    public static void main(String[] args) {
        new CmdImpl().startServer();
    }

    @Override
    public void stopServer() {

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
        else logger.info("Started fabric network");
    }

    private void exec(String... cmdArgs) throws IOException, InterruptedException {
        exec(null, cmdArgs);
    }

}
