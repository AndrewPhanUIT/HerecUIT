package uit.herec.service.org;

import uit.herec.common.dto.AppointmentDto;
import uit.herec.common.dto.DiagnosisDto;

public interface IBaseOrgService {
    String getCode();
    String getPhoneNumber();
    DiagnosisDto formatDiagnosis(String diagnosisDetailStr);
    AppointmentDto formatAppointment(String appointmentDetailStr);
}
