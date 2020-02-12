package uit.herec.service.org.quan12;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import uit.herec.common.dto.AppointmentDto;
import uit.herec.common.dto.DiagnosisDto;
import uit.herec.dao.entity.AppUser;
import uit.herec.dao.entity.Organization;
import uit.herec.dao.repository.OrganizationRepository;
import uit.herec.service.org.IBaseOrgService;
import uit.herec.service.org.quan12.form.Quan12AppointmentForm;
import uit.herec.service.org.quan12.form.Quan12AppointmentFormDetail;

@Service
public class Quan12Service implements IBaseOrgService{

	private final static Gson gson = new Gson();
	
	@Autowired
	private uit.herec.hyperledger.Service hyperledgerService;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
    @Value("${code.quan12}")
    private String code;
    
    @Override
    public DiagnosisDto formatDiagnosis(Object diagnosisForm) {
        return null;
    }

    @Override
    public AppointmentDto formatAppointment(Object appointmentForm, String...args) {
    	Quan12AppointmentFormDetail formDetail = appointmentForm instanceof Quan12AppointmentFormDetail ? (Quan12AppointmentFormDetail) appointmentForm : null;
    	if(formDetail == null) {
    		return null;
    	}
    	String orgFullName = "";
    	if (args[0] != null ) {
    		Organization org = this.organizationRepository.findByHyperledgerNameIgnoreCase(this.code)
    				.orElse(null);
    		orgFullName = org != null ? org.getName() : "";
    	}
    	AppointmentDto resultDto = new AppointmentDto(orgFullName, formDetail.getClinician(), formDetail.getCreatedAt(), formDetail.getAppointmentDate());
        return resultDto;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

	@Override
	public boolean addDiagnosis(String json) {
		return false;
	}

	@Override
	public boolean addAppointment(String json) {
		Quan12AppointmentForm appointmentForm = gson.fromJson(json, Quan12AppointmentForm.class);
		AppointmentDto dto = this.formatAppointment(appointmentForm.getData());
		String phoneNumber = appointmentForm.getPatientPhoneNumber();
		if (this.hyperledgerService.addNewAppoiment("Quan12", "herecchannel", phoneNumber, dto)) {
			
		}
		return false;
	}

}
