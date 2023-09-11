package meetona.data.service;

import meetona.core.Dto.request.UnitRequest;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.UnitDto;
import meetona.core.entity.Unit;
import meetona.core.exception.AppException;
import meetona.core.exception.SignupException;
import meetona.core.interfaces.IUnitService;
import meetona.data.mapper.UnitMapper;
import meetona.data.repository.UnitRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UnitService implements IUnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    public UnitService(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    @Override
    public ApiResponse<List<UnitDto>> getAll() {
        List<Unit> units = unitRepository.findAll();

        List<UnitDto> unitDto = units.stream()
                .map(unitMapper::ToUnitDto)
                .collect(Collectors.toList());

        ApiResponse<List<UnitDto>> response = new ApiResponse<>(unitDto, true);

        return response;
    }

    @Override
    public ApiResponse<UnitDto> getById(UUID id) {
        Optional<Unit> unitOptional = unitRepository.findById(id);

        Unit unit = unitOptional.orElse(null); // Unwrap the Optional to get a Unit or null

        UnitDto unitDto = unitMapper.ToUnitDto(unit);

        var response = new ApiResponse<>(unitDto, true);

        return response;
    }

    @Override
    @Async
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

        return response;
    }

    @Override
    @Transactional
    public ApiResponse<UnitDto> update(UUID id, UnitRequest unitRequest) {
        boolean isUnitExists = unitRepository.existsById(id);

        if(isUnitExists){
            throw new AppException("Id does not exists");
        }

        Unit newUnit = buildUnit(unitRequest);

        unitRepository.save(newUnit);
        UnitDto updatedUnit = unitMapper.ToUnitDto(newUnit);

        var response = new ApiResponse<>(updatedUnit, true);
        return response;
    }

    @Override
    @Transactional
    public ApiResponse<UnitDto> delete(UUID id) {
        boolean isUnitExists = unitRepository.existsById(id);

        if(isUnitExists){
            throw new AppException("Id does not exists");
        }

        unitRepository.deleteById(id);
        UnitDto deletedUnitDto = new UnitDto(id, null, null);

        var response = new ApiResponse<>(deletedUnitDto, true);
        return response;
    }

    private Unit buildUnit(UnitRequest unitRequest) {
        return Unit.builder()
                .name(unitRequest.name())
                .location(unitRequest.location())
                .build();
    }
}
