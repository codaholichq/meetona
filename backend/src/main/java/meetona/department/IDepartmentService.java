package meetona.department;

import meetona.shared.response.ServiceResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IDepartmentService {
    ServiceResponse<List<DepartmentDto>> getAll(Pageable pageable);
    ServiceResponse<DepartmentDto> getById(UUID id);
    ServiceResponse<DepartmentDto> add(DepartmentRequest request);
    ServiceResponse<DepartmentDto> update(UUID id, DepartmentRequest request);
    ServiceResponse<DepartmentDto> delete(UUID id);
}
