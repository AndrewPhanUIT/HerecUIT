package uit.herec.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import uit.herec.common.dto.AllergyDto;
import uit.herec.dao.entity.Allergy;

@Component
public class AllergyMapper {
    public AllergyDto mapEntityToDto(Allergy entity) {
        if(entity == null) return null;
        AllergyDto dto = new AllergyDto(entity.getName(), entity.getStatus(), entity.getReaction());
        return dto;
    }
    
    public List<AllergyDto> mapListEntityToListDto(Set<Allergy> allergies) {
        return allergies.stream().map(a -> this.mapEntityToDto(a)).collect(Collectors.toList());
    }
}
