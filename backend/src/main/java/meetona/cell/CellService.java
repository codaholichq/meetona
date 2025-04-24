package meetona.cell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.shared.exception.InsertionFailedException;
import meetona.shared.exception.ResourceNotFoundException;
import meetona.shared.response.ServiceResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CellService implements ICellService {

    private final CellMapper mapper;
    private final CellRepository cellRepository;
    private final CellActionProducer cellActionProducer;

    @Override
    @Cacheable("cells")
    public ServiceResponse<List<CellDto>> getAll(Pageable pageable) {
        Page<CellDto> cells = cellRepository.findAll(pageable).map(mapper::toDto);
        List<CellDto> cellDto = cells.getContent();

        log.info("Fetched cells => {}", cellDto);
        return new ServiceResponse<>(cellDto, true);
    }

    @Override
    @Cacheable("cell")
    public ServiceResponse<CellDto> getById(UUID id) {
        Cell cell = cellRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cell", "id", id));

        CellDto cellDto = mapper.toDto(cell);

        log.info("Fetched cell => {}", cellDto);
        return new ServiceResponse<>(cellDto, true);
    }

    @Override
    @Transactional
    public ServiceResponse<CellDto> add(CellRequest request) {
        if (cellRepository.existsByName(request.name())) {
            throw new InsertionFailedException(request.name(), "Cell name already exists");
        }

        Cell newCell = buildCell(request);
        newCell = cellRepository.save(newCell);

        CellDto cellDto = mapper.toDto(newCell);

        cellActionProducer.sendMessage(cellDto);
        return new ServiceResponse<>(cellDto, true);
    }

    @Override
    @Transactional
    @CacheEvict(value = "cell", key = "#cell.id")
    public ServiceResponse<CellDto> update(UUID id, CellRequest request) {
        if (cellRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cell", "id", id);
        }

        Cell newCell = buildCell(request);

        cellRepository.save(newCell);
        CellDto updatedCell = mapper.toDto(newCell);

        cellActionProducer.sendMessage(id, updatedCell);
        return new ServiceResponse<>(updatedCell, true);
    }

    @Override
    @Transactional
    @CacheEvict(value = "cell", key = "#id")
    public ServiceResponse<CellDto> delete(UUID id) {
        if (cellRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cell", "id", id);
        }

        cellRepository.deleteById(id);
        CellDto deletedCell = new CellDto(id, null, null);

        cellActionProducer.sendMessage(id);
        return new ServiceResponse<>(deletedCell, true);
    }

    private Cell buildCell(CellRequest request) {
        return Cell.builder()
                .name(request.name())
                .address(request.address())
                .build();
    }
}
