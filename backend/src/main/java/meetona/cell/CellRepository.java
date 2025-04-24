package meetona.cell;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CellRepository extends JpaRepository<Cell, UUID> {
    Boolean existsByName(String name);
}
