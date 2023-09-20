package meetona.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.entity.Unit;
import meetona.core.exception.AppException;
import meetona.core.exception.SignupException;
import meetona.core.interfaces.IUnitService;
import meetona.core.payload.request.UnitRequest;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.UnitDto;
import meetona.data.mapper.UnitMapper;
import meetona.data.messaging.producers.UnitActionProducer;
import meetona.data.repository.UnitRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UnitService implements IUnitService {

    private final UnitMapper unitMapper;
    private final UnitRepository unitRepository;
    private final UnitActionProducer unitActionProducer;

    @Override
    @Cacheable("units")
    public ApiResponse<List<UnitDto>> getAll() {
        List<Unit> units = unitRepository.findAll();

        List<UnitDto> unitDto = units.stream()
                .map(unitMapper::ToUnitDto)
                .collect(Collectors.toList());

        ApiResponse<List<UnitDto>> response = new ApiResponse<>(unitDto, true);

        log.info("Fetched users => {}", unitDto);
        return response;
    }

    @Override
    @Cacheable("unit")
    public ApiResponse<UnitDto> getById(UUID id) {
        Optional<Unit> unitOptional = unitRepository.findById(id);

        Unit unit = unitOptional.orElse(null); // Unwrap the Optional to get a Unit or null

        UnitDto unitDto = unitMapper.ToUnitDto(unit);

        var response = new ApiResponse<>(unitDto, true);

        log.info("Fetched unit => {}", unitDto);
        return response;
    }

    @Override
    @Transactional
    public ApiResponse<UnitDto> add(UnitRequest unitRequest) {
        boolean isNameExists = unitRepository.existsByName(unitRequest.name());

        if (isNameExists) {
            throw new SignupException(unitRequest.name(), "Unit name already exists");
        }

        Unit newUnit = buildUnit(unitRequest);
        unitRepository.save(newUnit);

        UnitDto unitDto = unitMapper.ToUnitDto(newUnit);
        var response = new ApiResponse<>(unitDto, true);

        unitActionProducer.sendMessage(unitDto);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "unit", key = "#unit.id")
    public ApiResponse<UnitDto> update(UUID id, UnitRequest unitRequest) {
        boolean isUnitExists = unitRepository.existsById(id);

        if(isUnitExists) {
            throw new AppException("Id does not exists");
        }

        Unit newUnit = buildUnit(unitRequest);

        unitRepository.save(newUnit);
        UnitDto updatedUnit = unitMapper.ToUnitDto(newUnit);

        var response = new ApiResponse<>(updatedUnit, true);

        unitActionProducer.sendMessage(id, updatedUnit);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "unit", key = "#id")
    public ApiResponse<UnitDto> delete(UUID id) {
        boolean isUnitExists = unitRepository.existsById(id);

        if(isUnitExists){
            throw new AppException("Id does not exists");
        }

        unitRepository.deleteById(id);
        UnitDto deletedUnitDto = new UnitDto(id, null, null);

        var response = new ApiResponse<>(deletedUnitDto, true);

        unitActionProducer.sendMessage(id);
        return response;
    }

    private Unit buildUnit(UnitRequest unitRequest) {
        return Unit.builder()
                .name(unitRequest.name())
                .location(unitRequest.location())
                .build();
    }
}
