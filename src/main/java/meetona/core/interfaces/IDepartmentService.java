package meetona.core.interfaces;

import meetona.core.payload.request.DepartmentRequest;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.DepartmentDto;
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
