package uit.herec.service.org.quan12;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import uit.herec.common.dto.AppointmentDto;
import uit.herec.common.dto.DiagnosisDto;
import uit.herec.service.org.IBaseOrgService;

@Service
public class Quan12Service implements IBaseOrgService{

    @Value("${code.quan12}")
    private String code;
    
    @Override
    public DiagnosisDto formatDiagnosis(String diagnosisDetailStr) {
        return null;
    }

    @Override
    public AppointmentDto formatAppointment(String appointmentDetailStr) {
        return null;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

}
