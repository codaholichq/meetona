package meetona.unit;

import meetona.shared.response.ApiResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUnitService {
    ApiResponse<List<UnitDto>> getAll(Pageable pageable);
    ApiResponse<UnitDto> getById(UUID id);
    ApiResponse<UnitDto> add(UnitRequest request);
    ApiResponse<UnitDto> update(UUID id, UnitRequest request);
    ApiResponse<UnitDto> delete(UUID id);
}
