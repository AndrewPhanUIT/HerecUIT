package uit.herec.service;

import java.util.List;

import uit.herec.common.dto.MedicationDto;
import uit.herec.dao.entity.Diagnosis;

public interface IMedicationService {
    void addMedications(Diagnosis diagnosis, List<MedicationDto> dtos);
}
