package meetona.cell;

import meetona.shared.response.ServiceResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ICellService {
    ServiceResponse<List<CellDto>> getAll(Pageable pageable);
    ServiceResponse<CellDto> getById(UUID id);
    ServiceResponse<CellDto> add(CellRequest request);
    ServiceResponse<CellDto> update(UUID id, CellRequest request);
    ServiceResponse<CellDto> delete(UUID id);
}
