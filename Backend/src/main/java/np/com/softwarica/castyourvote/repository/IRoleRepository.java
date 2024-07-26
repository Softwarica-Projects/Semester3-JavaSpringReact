package np.com.softwarica.castyourvote.repository;

import np.com.softwarica.castyourvote.entity.Role;
import np.com.softwarica.castyourvote.core.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
