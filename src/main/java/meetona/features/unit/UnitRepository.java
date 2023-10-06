package meetona.features.unit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UnitRepository extends JpaRepository<Unit, UUID> {
    Boolean existsByName(String name);
}
