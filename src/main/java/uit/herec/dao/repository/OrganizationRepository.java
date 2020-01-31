package uit.herec.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uit.herec.dao.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer>{

}
