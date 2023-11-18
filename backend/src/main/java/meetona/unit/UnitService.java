package meetona.unit;

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
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UnitService implements IUnitService {

    private final UnitMapper mapper;
    private final UnitRepository unitRepository;
    private final UnitActionProducer unitActionProducer;

    @Override
    @Cacheable("units")
    public ServiceResponse<List<UnitDto>> getAll(Pageable pageable) {
        Page<Unit> units = unitRepository.findAll(pageable);

        List<UnitDto> unitDto = units.stream()
                .map(mapper::toDto)
                .toList();

        ServiceResponse<List<UnitDto>> response = new ServiceResponse<>(unitDto, true);

        log.info("Fetched units => {}", unitDto);
        return response;
    }

    @Override
    @Cacheable("unit")
    public ServiceResponse<UnitDto> getById(UUID id) {
        Optional<Unit> unitOptional = unitRepository.findById(id);

        Unit unit = unitOptional.orElse(null); // Unwrap the Optional to get a Unit or null

        UnitDto unitDto = mapper.toDto(unit);

        var response = new ServiceResponse<>(unitDto, true);

        log.info("Fetched unit => {}", unitDto);
        return response;
    }

    @Override
    @Transactional
    public ServiceResponse<UnitDto> add(UnitRequest request) {
        boolean isNameExists = unitRepository.existsByName(request.name());

        if (isNameExists) {
            throw new InsertionFailedException(request.name(), "Unit name already exists");
        }

        Unit newUnit = buildUnit(request);
        unitRepository.save(newUnit);

        UnitDto unitDto = mapper.toDto(newUnit);
        var response = new ServiceResponse<>(unitDto, true);

        unitActionProducer.sendMessage(unitDto);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "unit", key = "#unit.id")
    public ServiceResponse<UnitDto> update(UUID id, UnitRequest request) {
        boolean isUnitExists = unitRepository.existsById(id);

        if(!isUnitExists) {
            throw new ResourceNotFoundException("User", "id", id);
        }

        Unit newUnit = buildUnit(request);

        unitRepository.save(newUnit);
        UnitDto updatedUnit = mapper.toDto(newUnit);

        var response = new ServiceResponse<>(updatedUnit, true);

        unitActionProducer.sendMessage(id, updatedUnit);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "unit", key = "#id")
    public ServiceResponse<UnitDto> delete(UUID id) {
        boolean isUnitExists = unitRepository.existsById(id);

        if(!isUnitExists){
            throw new ResourceNotFoundException("User", "id", id);
        }

        unitRepository.deleteById(id);
        UnitDto deletedUnitDto = new UnitDto(id, null, null);

        var response = new ServiceResponse<>(deletedUnitDto, true);

        unitActionProducer.sendMessage(id);
        return response;
    }

    private Unit buildUnit(UnitRequest request) {
        return Unit.builder()
                .name(request.name())
                .address(request.address())
                .build();
    }
}
