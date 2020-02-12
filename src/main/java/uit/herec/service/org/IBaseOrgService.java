package uit.herec.service.org;

import uit.herec.common.dto.AppointmentDto;
import uit.herec.common.dto.DiagnosisDto;

public interface IBaseOrgService {
    String getCode();
    String getPhoneNumber();
    DiagnosisDto formatDiagnosis(Object diagnosisDetailStr, String...args);
    AppointmentDto formatAppointment(Object appointmentDetailStr, String...args);
    boolean addDiagnosis(String json);
    boolean addAppointment(String json);
}
