package uit.herec.hyperledger;

import java.util.List;

import uit.herec.common.dto.ChaincodeScript;

public interface Cmd {
    void startServer();
    void stopServer();
    boolean invokeChaincode(String orgName, String peerName, String peerPort, String channel, String chaincode, 
            ChaincodeScript script, List<String> orgs, List<String> ports, List<String> peers);
    boolean addPermission(String orgName, String peerPort, String channel, String chaincode);
    String queryChaincode(String orgName, String peerName, String peerPort, String channel, String chaincode, 
            ChaincodeScript script);
}
