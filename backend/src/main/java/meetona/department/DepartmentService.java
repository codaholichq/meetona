package meetona.department;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.shared.exception.AppException;
import meetona.shared.exception.InsertionFailedException;
import meetona.shared.exception.ResourceNotFoundException;
import meetona.shared.response.ApiResponse;
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
public class DepartmentService implements IDepartmentService {

    private final DepartmentMapper mapper;
    private final DepartmentRepository repository;
//    private final MemberActionProducer memberActionProducer;

    @Override
    @Cacheable("departments")
    public ApiResponse<List<DepartmentDto>> getAll(Pageable pageable) {
        Page<Department> departments = repository.findAll(pageable);

        List<DepartmentDto> departmentDto = departments.stream()
                .map(mapper::toDto)
                .toList();

        var response = new ApiResponse<>(departmentDto, true);

        log.info("Fetched units => {}", departmentDto);
        return response;
    }

    @Override
    @Cacheable("department")
    public ApiResponse<DepartmentDto> getById(UUID id) {
        Department department = repository.findById(id).orElse(null);

        DepartmentDto memberDto = mapper.toDto(department);

        var response = new ApiResponse<>(memberDto, true);

        log.info("Fetched unit => {}", memberDto);
        return response;
    }

    @Override
    @Transactional
    public ApiResponse<DepartmentDto> add(DepartmentRequest request) {
        boolean isNameExists = repository.existsByName(request.name());
        boolean isLeadExists = repository.existsByLead(request.lead());

        if (isNameExists) {
            throw new InsertionFailedException(request.name(), " already exists");
        }

        if (isLeadExists) {
            throw new InsertionFailedException(request.lead(), " already exists");
        }

        Department newDepartment = buildDepartment(request);
        repository.save(newDepartment);

        DepartmentDto departmentDto = mapper.toDto(newDepartment);
        var response = new ApiResponse<>(departmentDto, true);

//        unitActionProducer.sendMessage(unitDto);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "department", key = "#department.id")
    public ApiResponse<DepartmentDto> update(UUID id, DepartmentRequest request) {
        boolean isUnitExists = repository.existsById(id);

        if(!isUnitExists) {
            throw new AppException("Id does not exists");
        }

        Department newDepartment = buildDepartment(request);

        repository.save(newDepartment);
        DepartmentDto updatedDepartment = mapper.toDto(newDepartment);

        var response = new ApiResponse<>(updatedDepartment, true);

//        memberActionProducer.sendMessage(id, updatedUnit);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "department", key = "#id")
    public ApiResponse<DepartmentDto> delete(UUID id) {
        boolean isDepartmentExists = repository.existsById(id);

        if(!isDepartmentExists){
            throw new ResourceNotFoundException("User", "id", id);
        }

        repository.deleteById(id);
        var deletedDepartment = new DepartmentDto(id, null, null);

        var response = new ApiResponse<>(deletedDepartment, true);

//        memberActionProducer.sendMessage(id);
        return response;
    }

    private Department buildDepartment(DepartmentRequest request) {

        return Department.builder()
                .name(request.name())
                .lead(request.lead())
                .build();
    }
}
