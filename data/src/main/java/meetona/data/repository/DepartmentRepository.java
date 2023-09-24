package meetona.data.repository;

import meetona.core.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    boolean existsByName(String name);
    boolean existsByLead(String lead);
}
