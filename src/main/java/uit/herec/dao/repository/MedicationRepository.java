package uit.herec.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uit.herec.dao.entity.Medication;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer>{

}
