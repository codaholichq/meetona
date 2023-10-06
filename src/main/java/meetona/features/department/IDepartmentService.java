package meetona.features.department;

import meetona.shared.response.ApiResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IDepartmentService {
    ApiResponse<List<DepartmentDto>> getAll(Pageable pageable);
    ApiResponse<DepartmentDto> getById(UUID id);
    ApiResponse<DepartmentDto> add(DepartmentRequest request);
    ApiResponse<DepartmentDto> update(UUID id, DepartmentRequest request);
    ApiResponse<DepartmentDto> delete(UUID id);
}
