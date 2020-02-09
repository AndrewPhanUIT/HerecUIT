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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uit.herec.common.APIConstants;
import uit.herec.common.dto.ApiResponseDto;
import uit.herec.common.dto.AppointmentDetailDto;
import uit.herec.common.dto.DiagnosisDetailDto;
import uit.herec.common.message.Error;
import uit.herec.hyperledger.Service;
import uit.herec.service.IDiagnosisService;
import uit.herec.service.IUserService;

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
    
    @GetMapping("/diagnosis")
    public ResponseEntity<ApiResponseDto> queryDiagnosis(@RequestParam String hyperledgerName, @RequestParam String key) {
        DiagnosisDetailDto dto = this.diagnosisService.getDiagnosisDetailByKey(hyperledgerName, key);
        if (dto == null) {
            return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(false, null, Error.DIAGNOSIS_NOT_FOUND), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponseDto(true, Arrays.asList(dto), ""));
    }
    
    
}
