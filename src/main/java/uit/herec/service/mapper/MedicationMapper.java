package uit.herec.service.mapper;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import uit.herec.common.dto.MedicationDto;
import uit.herec.dao.entity.Medication;

@Component
public class MedicationMapper {
    MedicationDto mapEntityToDto(Medication entity) {
        if (entity == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        MedicationDto dto = new MedicationDto(entity.getQuantity(), entity.getDoseQuantity(), entity.getName(),
                entity.getNote(), sdf.format(entity.getEndDate()), sdf.format(entity.getStartDate()));
        return dto;
    }

    List<MedicationDto> mapSetEntityToListDto(Set<Medication> medications) {
        return medications.stream().map(m -> this.mapEntityToDto(m)).collect(Collectors.toList());
    }
}
