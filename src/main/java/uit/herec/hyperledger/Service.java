package uit.herec.hyperledger;

import java.util.List;

import uit.herec.common.dto.AppointmentDetailDto;
import uit.herec.common.dto.AppointmentDto;
import uit.herec.common.dto.DiagnosisDetailDto;
import uit.herec.common.dto.DiagnosisDto;

public interface Service {
    boolean enrollAdmin(String msp, String orgName, String caHost);
    boolean registerUser(String username, String msp, String orgName, String caHost, int departmentNumber);
    List<DiagnosisDetailDto> queryAllDiagnosisByPhoneNumber(String username, String msp, String orgName, String channel, String chaincode, String phoneNumber);
    List<AppointmentDetailDto> queryAllAppointmentsByPhoneNumber(String username, String msp, String orgName, String channel, String chaincode, String phoneNumber);
    boolean addNewDiagnosis(String orgName, String channel, String phoneNumber, DiagnosisDto diagnosisDto);
    boolean addNewAppoiment(String orgName, String channel, String phoneNumber, AppointmentDto appointmentDto);
}
