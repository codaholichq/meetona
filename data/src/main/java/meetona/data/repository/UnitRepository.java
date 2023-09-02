package meetona.data.repository;

import meetona.core.Dto.response.UnitDto;
import meetona.core.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UnitRepository extends JpaRepository<Unit, UUID> {
    Unit getById(UUID id);
    UnitDto getAll();
    Boolean existsByName(String name);
    Boolean existsByLocation(String location);
}
