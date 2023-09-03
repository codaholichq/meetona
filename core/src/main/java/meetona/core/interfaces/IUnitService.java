package meetona.core.interfaces;

import meetona.core.Dto.request.UnitRequest;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.UnitDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IUnitService {
    CompletableFuture<ApiResponse<List<UnitDto>>> getAll();
    CompletableFuture<ApiResponse<UnitDto>> getById(UUID id);
    CompletableFuture<ApiResponse<UnitDto>> add(UnitRequest unitRequest);
    CompletableFuture<ApiResponse<UnitDto>> update(UUID id, UnitRequest unitRequest);
    CompletableFuture<ApiResponse<UnitDto>> delete(UUID id);
}
