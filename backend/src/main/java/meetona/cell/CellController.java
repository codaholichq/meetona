package meetona.cell;

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
@RequestMapping("/api/cell")
public class CellController {

    private final ICellService cellService;

    public CellController(ICellService cellService) {
        this.cellService = cellService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<List<CellDto>>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(cellService.getAll(pageable));
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<CellDto>> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(cellService.getById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<CellDto>> add(@Valid @RequestBody CellRequest request) {
        return ResponseEntity.ok(cellService.add(request));
    }

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<CellDto>> update(
            @PathVariable("id") UUID id,
            @RequestBody CellRequest request
    ) {
        return ResponseEntity.ok(cellService.update(id, request));
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<CellDto>> delete(@PathVariable("id") UUID id){
        return ResponseEntity.ok(cellService.delete(id));
    }
}
