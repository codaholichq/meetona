package meetona.unit;

import meetona.shared.response.ServiceResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUnitService {
    ServiceResponse<List<UnitDto>> getAll(Pageable pageable);
    ServiceResponse<UnitDto> getById(UUID id);
    ServiceResponse<UnitDto> add(UnitRequest request);
    ServiceResponse<UnitDto> update(UUID id, UnitRequest request);
    ServiceResponse<UnitDto> delete(UUID id);
}
