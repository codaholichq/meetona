package meetona.core.interfaces;

import meetona.core.Dto.request.AddUnitDto;
import meetona.core.Dto.request.UpdateUnitDto;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.UnitDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IUnitService {
    CompletableFuture<ApiResponse<UnitDto>> getAll();
    CompletableFuture<ApiResponse<UnitDto>> getById(UUID id);
    CompletableFuture<ApiResponse<UnitDto>> add(AddUnitDto addUnitDto);
    CompletableFuture<ApiResponse<UnitDto>> update(UUID id, UpdateUnitDto updateUnitDto);
    CompletableFuture<ApiResponse<UnitDto>> delete(UUID id);
}
