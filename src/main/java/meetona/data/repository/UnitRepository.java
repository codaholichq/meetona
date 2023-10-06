package meetona.data.repository;

import meetona.core.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UnitRepository extends JpaRepository<Unit, UUID> {
    Boolean existsByName(String name);
}
