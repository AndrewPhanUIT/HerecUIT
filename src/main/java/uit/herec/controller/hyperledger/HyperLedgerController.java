package uit.herec.controller.hyperledger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import uit.herec.common.APIConstants;
import uit.herec.common.dto.ApiResponseDto;
import uit.herec.common.dto.AppointmentDetailDto;
import uit.herec.common.dto.DiagnosisDetailDto;
import uit.herec.common.form.Form;
import uit.herec.common.message.Error;
import uit.herec.common.message.Success;
import uit.herec.hyperledger.Service;
import uit.herec.service.IAppointmentService;
import uit.herec.service.IDiagnosisService;
import uit.herec.service.IUserService;
import uit.herec.service.org.BaseOrgService;
import uit.herec.service.org.IBaseOrgService;

@RestController
@RequestMapping(APIConstants.HYPERLEDGER_API)
@CrossOrigin("*")
public class HyperLedgerController {

    @Autowired
    private Service hyperledgerService;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IDiagnosisService diagnosisService;
    
    @Autowired
    private IAppointmentService appointmentService;
    
    @Autowired
    private BaseOrgService baseOrgService;

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("")
    public ResponseEntity<ApiResponseDto> query(@RequestParam String hyperledgerName) {
        String phoneNumber = this.userService.getPhoneNumber(hyperledgerName);
        List<DiagnosisDetailDto> diagnosis = this.hyperledgerService.queryAllDiagnosisByPhoneNumber(hyperledgerName,
                "ClientMSP", "Client", "herecchannel", "diagnosis", phoneNumber);
        List<AppointmentDetailDto> appointments = this.hyperledgerService.queryAllAppointmentsByPhoneNumber(
                hyperledgerName, "ClientMSP", "Client", "herecchannel", "diagnosis", phoneNumber);
        List<Object> objs = new ArrayList<Object>();
        objs.add(diagnosis);
        objs.add(appointments);
        ApiResponseDto dto = new ApiResponseDto();
        dto.setSuccess(true);
        dto.setDatas(objs);
        return ResponseEntity.ok(dto);
    }
    
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/all/diagnosis")
    public ResponseEntity<ApiResponseDto> queryAllDiagnosis(@RequestParam String hyperledgerName) {
        String phoneNumber = this.userService.getPhoneNumber(hyperledgerName);
        List<DiagnosisDetailDto> diagnosis = this.hyperledgerService.queryAllDiagnosisByPhoneNumber(hyperledgerName,
                "ClientMSP", "Client", "herecchannel", "diagnosis", phoneNumber);
        List<Object> objs = new ArrayList<Object>();
        objs.add(diagnosis);
        ApiResponseDto dto = new ApiResponseDto();
        dto.setSuccess(true);
        dto.setDatas(objs);
        return ResponseEntity.ok(dto);
    }
    
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("")
    public ResponseEntity<ApiResponseDto> queryAllAppointments(@RequestParam String hyperledgerName) {
        String phoneNumber = this.userService.getPhoneNumber(hyperledgerName);
        List<AppointmentDetailDto> appointments = this.hyperledgerService.queryAllAppointmentsByPhoneNumber(
                hyperledgerName, "ClientMSP", "Client", "herecchannel", "diagnosis", phoneNumber);
        List<Object> objs = new ArrayList<Object>();
        objs.add(appointments);
        ApiResponseDto dto = new ApiResponseDto();
        dto.setSuccess(true);
        dto.setDatas(objs);
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/diagnosis")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<ApiResponseDto> queryDiagnosis(@RequestParam String hyperledgerName, @RequestParam String key) {
        DiagnosisDetailDto dto = this.diagnosisService.getDiagnosisDetailByKey(hyperledgerName, key);
        if (dto == null) {
            return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(false, null, Error.DIAGNOSIS_NOT_FOUND), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponseDto(true, Arrays.asList(dto), ""));
    }
    
    @GetMapping("/appointment")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<ApiResponseDto> queryAppointment(@RequestParam String hyperledgerName, @RequestParam String key) {
        AppointmentDetailDto dto = this.appointmentService.getAppointmentByKey(hyperledgerName, key);
        if(dto == null) {
            return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(false, null, Error.APPOINTMENT_NOT_FOUND), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponseDto(true, Arrays.asList(dto), ""));
    }
    
    @PostMapping("/add/diagnosis")
    public ResponseEntity<ApiResponseDto> addNewDiagnosis(@RequestBody String json) {
        String convertedJson = json.substring(1, json.length() - 1);
        Gson gson = new Gson();
        Form form = gson.fromJson(convertedJson, Form.class);
        IBaseOrgService orgService = this.baseOrgService.getService(form.getCode());
        if (orgService.addDiagnosis(convertedJson)) {
            return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(true, Arrays.asList(""), Success.ADD_DIAGNOSIS), HttpStatus.OK);
        }
        return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(false, Arrays.asList(""), Error.CANT_ADD_DIAGNOSIS), HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/add/appointment")
    public ResponseEntity<ApiResponseDto> addNewAppointment(@RequestBody String json) {
    	String convertedJson = json.substring(1, json.length() - 1);
    	Gson gson = new Gson();
    	Form form = gson.fromJson(convertedJson, Form.class);
    	IBaseOrgService orgService = this.baseOrgService.getService(form.getCode());
    	if (orgService.addAppointment(convertedJson)) {
    	    return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(true, Arrays.asList(""), Success.ADD_APPOINTMENT), HttpStatus.OK);
    	}
    	return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(false, Arrays.asList(""), Error.CANT_ADD_APPOINTMENT), HttpStatus.BAD_REQUEST);
    }
}
