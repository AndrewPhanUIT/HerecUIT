package uit.herec.service.org.tanphu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;

import uit.herec.common.dto.AllergyDto;
import uit.herec.common.dto.AppointmentDto;
import uit.herec.common.dto.DiagnosisDto;
import uit.herec.common.dto.MedicationDto;
import uit.herec.dao.entity.AppUser;
import uit.herec.dao.entity.Appointment;
import uit.herec.dao.entity.Diagnosis;
import uit.herec.dao.entity.Organization;
import uit.herec.service.IAllergyService;
import uit.herec.service.IAppointmentService;
import uit.herec.service.IDiagnosisService;
import uit.herec.service.IMedicationService;
import uit.herec.service.IOrganizationService;
import uit.herec.service.IUserService;
import uit.herec.service.org.IBaseOrgService;
import uit.herec.service.org.quan12.Quan12Service;
import uit.herec.service.org.tanphu.form.TanPhuAppointmentForm;
import uit.herec.service.org.tanphu.form.TanPhuAppointmentFormDetail;
import uit.herec.service.org.tanphu.form.TanPhuDiagnosisForm;
import uit.herec.service.org.tanphu.form.TanPhuDiagnosisFormDetail;

public class TanPhuService implements IBaseOrgService {

    private final static Gson gson = new Gson();
    private final static Logger logger = LoggerFactory.getLogger(Quan12Service.class);
    
    @Autowired
    private uit.herec.hyperledger.Service hyperledgerService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrganizationService orgService;

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IDiagnosisService diagnosisService;

    @Autowired
    private IAllergyService allergyService;

    @Autowired
    private IMedicationService medicationService;

    @Value("${code.tanphu}")
    private String code;
    
    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getPhoneNumber() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DiagnosisDto formatDiagnosis(Object diagnosisForm, String... args) {
        TanPhuDiagnosisFormDetail formDetail = diagnosisForm instanceof TanPhuDiagnosisFormDetail
                ? (TanPhuDiagnosisFormDetail) diagnosisForm
                : null;
        if (formDetail == null)
            return null;
        Organization org = this.orgService.getOrgByHyperledgerName(this.code);
        String orgFullName = org != null ? org.getName() : "";
        String createdAt = "";
        if (args[0] != null) {
            createdAt = args[0];
        }
        DiagnosisDto resultDto = new DiagnosisDto(orgFullName, formDetail.getClinician().getName(), createdAt);
        List<String> symptons = Arrays.asList(formDetail.getSymptons().split(","));
        List<MedicationDto> medicationDtos = formDetail.getMedications().stream().map(medication -> {
            MedicationDto dto = new MedicationDto(Integer.parseInt(medication.getQuantity()),
                    medication.getDoseQuantity(), medication.getName(), medication.getNote(), medication.getEndDate(),
                    medication.getStartDate());
            return dto;
        }).collect(Collectors.toList());
        List<AllergyDto> allergyDtos = formDetail.getAllergies().stream().map(allergy -> {
            AllergyDto dto = new AllergyDto(allergy.getName(), allergy.getStatus(), allergy.getReaction());
            return dto;
        }).collect(Collectors.toList());
        resultDto.setSymptons(symptons);
        resultDto.setMedications(medicationDtos);
        resultDto.setAllergies(allergyDtos);
        return resultDto;
    }

    @Override
    public AppointmentDto formatAppointment(Object appointmentForm, String... args) {
        TanPhuAppointmentFormDetail formDetail = appointmentForm instanceof TanPhuAppointmentFormDetail
                ? (TanPhuAppointmentFormDetail) appointmentForm
                : null;
        if (formDetail == null) {
            return null;
        }
        Organization org = this.orgService.getOrgByHyperledgerName(this.code);
        String orgFullName = org != null ? org.getName() : "";
        AppointmentDto resultDto = new AppointmentDto(orgFullName, formDetail.getClinician(), formDetail.getCreatedAt(),
                formDetail.getAppointmentDate());
        return resultDto;
    }

    @Override
    public boolean addDiagnosis(String json) {
        TanPhuDiagnosisForm diagnosisForm = gson.fromJson(json, TanPhuDiagnosisForm.class);
        DiagnosisDto dto = this.formatDiagnosis(diagnosisForm.getData(), diagnosisForm.getCreatedAt());
        String phoneNumber = diagnosisForm.getPatientPhoneNumber();
        if (this.hyperledgerService.addNewDiagnosis("TanPhu", "herecchannel", phoneNumber, dto)) {
            AppUser appUser = this.userService.getUserByPhoneNumber(phoneNumber);
            Organization org = this.orgService.getOrgByHyperledgerName(this.code);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date createdAt = null;
            try {
                createdAt = sdf.parse(dto.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String symptons = "";
            for (String sympton : dto.getSymptons()) {
                symptons += sympton + ", ";
            }
            Diagnosis diagnosis = new Diagnosis(appUser, org, dto.getClincian(), createdAt, symptons);
            int count = this.diagnosisService.count() + 1;
            diagnosis.setKey(String.format("D%03d", count));
            this.diagnosisService.addNewDiagnosis(diagnosis);
            this.allergyService.addAllergies(diagnosis, dto.getAllergies());
            this.medicationService.addMedications(diagnosis, dto.getMedications());
            return true;
        }
        return false;
    }

    @Override
    public boolean addAppointment(String json) {
        TanPhuAppointmentForm appointmentForm = gson.fromJson(json, TanPhuAppointmentForm.class);
        AppointmentDto dto = this.formatAppointment(appointmentForm.getData());
        String phoneNumber = appointmentForm.getPatientPhoneNumber();
        if (this.hyperledgerService.addNewAppoiment("TanPhu", "herecchannel", phoneNumber, dto)) {
            AppUser appUser = this.userService.getUserByPhoneNumber(phoneNumber);
            Organization org = this.orgService.getOrgByHyperledgerName(this.code);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date createdAt = null;
            Date appointmentTime = null;
            try {
                createdAt = sdf.parse(dto.getCreatedAt());
                appointmentTime = sdf.parse(dto.getAppointmentDate());
            } catch (ParseException e) {
                logger.error("Cant format Date:", e);
                e.printStackTrace();
            }
            Appointment appointment = new Appointment(appUser, org, createdAt, appointmentTime);
            int count = this.appointmentService.countAppointment() + 1;
            appointment.setKey(String.format("A%03d", count));
            appointment.setClinician(dto.getClincian());
            return this.appointmentService.addNewAppointment(appointment);
        }
        return false;
    }

}
