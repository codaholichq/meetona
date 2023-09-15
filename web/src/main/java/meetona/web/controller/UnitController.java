package meetona.web.controller;

import jakarta.validation.Valid;
import meetona.core.payload.request.UnitRequest;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.UnitDto;
import meetona.core.interfaces.IUnitService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api/unit",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class UnitController {

    private final IUnitService unitService;

    public UnitController(IUnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UnitDto>>> getAll() {
        return ResponseEntity.ok(unitService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<UnitDto>> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(unitService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UnitDto>> add(@Valid @RequestBody UnitRequest request) {
        return ResponseEntity.ok(unitService.add(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<UnitDto>> update(
            @PathVariable("id") UUID id,
            @RequestBody UnitRequest request
    ) {
        return ResponseEntity.ok(unitService.update(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<UnitDto>> delete(@PathVariable("id") UUID id){
        return ResponseEntity.ok(unitService.delete(id));
    }
}
