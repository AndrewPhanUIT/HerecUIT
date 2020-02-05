package uit.herec.hyperledger;

import uit.herec.common.dto.AppointmentDetailDto;
import uit.herec.common.dto.AppointmentDto;
import uit.herec.common.dto.DiagnosisDetailDto;
import uit.herec.common.dto.DiagnosisDto;

public interface Service {
    boolean enrollAdmin(String msp, String orgName, String caHost);
    boolean registerUser(String username, String msp, String orgName, String caHost, int departmentNumber);
    DiagnosisDetailDto queryAllDiagnosisByPhoneNumber(String username, String msp, String orgName, String channel, String chaincode, String phoneNumber);
    AppointmentDetailDto queryAllAppointmentsByPhoneNumber(String username, String msp, String orgName, String channel, String chaincode, String phoneNumber);
    boolean addNewDiagnosis(String msp, String orgName, String caHost, String channel, String chaincode, DiagnosisDetailDto dto, DiagnosisDto diagnosisDto);
    boolean addNewAppoiment(String msp, String orgName, String caHost, String channel, String chaincode, AppointmentDetailDto dto, AppointmentDto appointmentDto);
}
