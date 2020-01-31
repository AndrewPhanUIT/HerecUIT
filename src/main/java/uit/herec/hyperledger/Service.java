package uit.herec.hyperledger;

import uit.herec.common.dto.DiagnosisDetailDto;

public interface Service {
    boolean enrollAdmin(String msp, String orgName, String caHost);
    boolean registerUser(String username, String msp, String orgName, String caHost, int departmentNumber);
    DiagnosisDetailDto queryAllDiagnosisByPhoneNumber(String username, String msp, String orgName, String channel, String chaincode, String phoneNumber);
    
}
