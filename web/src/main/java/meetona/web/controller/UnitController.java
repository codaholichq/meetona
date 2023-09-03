package meetona.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import meetona.core.Dto.request.UnitRequest;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.UnitDto;
import meetona.core.interfaces.IUnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unit")
public class UnitController {

    private final IUnitService unitService;

    @GetMapping("/")
    public CompletableFuture<ResponseEntity<ApiResponse<List<UnitDto>>>> getAll() {
        return unitService
                .getAll()
                .thenApplyAsync(ResponseEntity::ok);
    }

    @GetMapping("{id}")
    public CompletableFuture<ResponseEntity<ApiResponse<UnitDto>>> getById(@RequestParam UUID id) {
        return unitService
                .getById(id)
                .thenApplyAsync(ResponseEntity::ok);
    }

    @PostMapping("/")
    public CompletableFuture<ResponseEntity<ApiResponse<UnitDto>>> add(@Valid @RequestBody UnitRequest request) {
        return unitService
                .add(request)
                .thenApplyAsync(ResponseEntity::ok);
    }

    @PutMapping("{id}")
    public CompletableFuture<ResponseEntity<ApiResponse<UnitDto>>> update(UUID id, @RequestBody UnitRequest request){
        return unitService
                .update(id, request)
                .thenApplyAsync(ResponseEntity::ok);
    }

    @DeleteMapping("{id}")
    public CompletableFuture<ResponseEntity<ApiResponse<UnitDto>>> delete(UUID id){
        return unitService
                .delete(id)
                .thenApplyAsync(ResponseEntity::ok);
    }
}
