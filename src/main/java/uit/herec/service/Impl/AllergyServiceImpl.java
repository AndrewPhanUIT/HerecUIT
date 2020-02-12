package uit.herec.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.herec.common.dto.AllergyDto;
import uit.herec.dao.entity.Allergy;
import uit.herec.dao.entity.Diagnosis;
import uit.herec.dao.repository.AllergyRepository;
import uit.herec.service.IAllergyService;

@Service
public class AllergyServiceImpl implements IAllergyService{

    @Autowired
    private AllergyRepository repository;
    
    @Override
    public void addAllergies(Diagnosis diagnosis, List<AllergyDto> allergyDtos) {
        List<Allergy> allergies = allergyDtos.stream().map(allergy -> {
            Allergy entity = new Allergy(diagnosis, allergy.getName(), allergy.getStatus(), allergy.getReaction());
            return entity;
        }).collect(Collectors.toList());
        this.repository.saveAll(allergies);
    }

}
