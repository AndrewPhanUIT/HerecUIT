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
    List<DiagnosisDetailDto> queryAllDiagnosisByPhoneNumberCmd(String username, String orgName, String channel, String chaincode, String phoneNumber);
    List<AppointmentDetailDto> queryAllAppointmentsByPhoneNumber(String username, String msp, String orgName, String channel, String chaincode, String phoneNumber);
    List<AppointmentDetailDto> queryAllAppointmentsByPhoneNumberCmd(String username, String orgName, String channel, String chaincode, String phoneNumber);
    boolean addNewDiagnosis(String orgName, String channel, String phoneNumber, DiagnosisDto diagnosisDto);
    boolean addNewAppoiment(String orgName, String channel, String phoneNumber, AppointmentDto appointmentDto);
    DiagnosisDetailDto queryDiagnosis(String userName, String orgName, String channel, String chaincode, String key);
    DiagnosisDetailDto queryDiagnosisCmd(String userName, String orgName, String channel, String chaincode, String key);
    AppointmentDetailDto queryAppointment(String userName, String orgName, String channel, String chaincode, String key);
    AppointmentDetailDto queryAppointmentCmd(String userName, String orgName, String channel, String chaincode, String key);
    int countIndexAppointment();
    int countIndexDiagnosis();
    boolean addPermission(String orgHyperledgerName, String phoneNumber);
}
