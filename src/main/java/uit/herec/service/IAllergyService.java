package uit.herec.service;

import java.util.List;

import uit.herec.common.dto.AllergyDto;
import uit.herec.dao.entity.Diagnosis;

public interface IAllergyService {
    void addAllergies(Diagnosis diagnosis, List<AllergyDto> allergyDtos);
}
