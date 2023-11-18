package meetona.unit;

import jakarta.validation.Valid;
import meetona.shared.response.ServiceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/unit")
public class UnitController {

    private final IUnitService unitService;

    public UnitController(IUnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<List<UnitDto>>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(unitService.getAll(pageable));
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<UnitDto>> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(unitService.getById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<UnitDto>> add(@Valid @RequestBody UnitRequest request) {
        return ResponseEntity.ok(unitService.add(request));
    }

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<UnitDto>> update(
            @PathVariable("id") UUID id,
            @RequestBody UnitRequest request
    ) {
        return ResponseEntity.ok(unitService.update(id, request));
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<UnitDto>> delete(@PathVariable("id") UUID id){
        return ResponseEntity.ok(unitService.delete(id));
    }
}
