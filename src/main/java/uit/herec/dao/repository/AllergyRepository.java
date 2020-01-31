package uit.herec.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uit.herec.dao.entity.Allergy;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Integer>{

}
