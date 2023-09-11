package meetona.core.interfaces;

import meetona.core.Dto.request.UnitRequest;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.UnitDto;

import java.util.List;
import java.util.UUID;

public interface IUnitService {
    ApiResponse<List<UnitDto>> getAll();
    ApiResponse<UnitDto> getById(UUID id);
    ApiResponse<UnitDto> add(UnitRequest unitRequest);
    ApiResponse<UnitDto> update(UUID id, UnitRequest unitRequest);
    ApiResponse<UnitDto> delete(UUID id);
}
