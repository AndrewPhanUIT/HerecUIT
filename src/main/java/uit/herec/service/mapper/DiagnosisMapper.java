package uit.herec.service.mapper;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uit.herec.common.dto.AllergyDto;
import uit.herec.common.dto.DiagnosisDetailDto;
import uit.herec.common.dto.DiagnosisDto;
import uit.herec.common.dto.MedicationDto;
import uit.herec.dao.entity.Diagnosis;

@Component
public class DiagnosisMapper {
    
    @Autowired
    private AllergyMapper allergyMapper;
    
    @Autowired
    private MedicationMapper medicationMapper;
    
    public DiagnosisDetailDto mapDiagnosisToDiagnosisDetail(Diagnosis diagnosis, String phoneNumber) {
        if (diagnosis == null)  return null;
        DiagnosisDetailDto dto = new DiagnosisDetailDto();
        dto.setPhoneNumber(phoneNumber);
        dto.setKey(diagnosis.getKey());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DiagnosisDto diagnosisDto = new DiagnosisDto(diagnosis.getOrganization().getName(), diagnosis.getClinician(), sdf.format(diagnosis.getCreatedAt()));
        List<String> symptons = Arrays.asList(diagnosis.getSymtoms().split(","));
        List<AllergyDto> allergyDtos = this.allergyMapper.mapListEntityToListDto(diagnosis.getAllergies());
        List<MedicationDto> medicationDtos = this.medicationMapper.mapSetEntityToListDto(diagnosis.getMedications());
        diagnosisDto.setSymptons(symptons);
        diagnosisDto.setAllergies(allergyDtos);
        diagnosisDto.setMedications(medicationDtos);
        dto.setDiagnosis(diagnosisDto);
        return dto;
    }
}
