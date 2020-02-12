package uit.herec.service.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.herec.common.dto.MedicationDto;
import uit.herec.dao.entity.Diagnosis;
import uit.herec.dao.entity.Medication;
import uit.herec.dao.repository.MedicationRepository;
import uit.herec.service.IMedicationService;

@Service
public class MedicationServiceImpl implements IMedicationService {

    @Autowired
    private MedicationRepository repository;

    @Override
    public void addMedications(Diagnosis diagnosis, List<MedicationDto> dtos) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        List<Medication> medications = dtos.stream().map(medication -> {
            Medication entity = null;
            try {
                entity = new Medication(diagnosis, medication.getQuantity(), medication.getDoseQuantity(), medication.getNote(),
                        medication.getName(), sdf.parse(medication.getStartDate()), sdf.parse(medication.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return entity;
        }).collect(Collectors.toList());
        this.repository.saveAll(medications);
    }

}
