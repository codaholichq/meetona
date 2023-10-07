package meetona.features.role;

import meetona.shared.enums.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(AppRole role);
//    boolean existsRole();
}
