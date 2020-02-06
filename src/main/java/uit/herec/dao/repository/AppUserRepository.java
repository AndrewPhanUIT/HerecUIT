package uit.herec.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uit.herec.dao.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer>{
    Optional<AppUser> findByPhoneNumber(String phoneNumber);
    Optional<AppUser> findByHyperledgerName(String hyHyperledgerName);
    List<AppUser> findByIdIn(List<Integer> userIds);
    Boolean existsByPhoneNumber(String phoneNumber);
}
