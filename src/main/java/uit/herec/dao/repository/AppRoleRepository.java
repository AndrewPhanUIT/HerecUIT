package uit.herec.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uit.herec.dao.entity.AppRole;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Integer>{
    Optional<AppRole> findByRole(String role);

}
