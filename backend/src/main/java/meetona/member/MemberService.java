package meetona.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.department.Department;
import meetona.department.DepartmentRepository;
import meetona.shared.exception.ResourceNotFoundException;
import meetona.unit.UnitRepository;
import meetona.shared.exception.AppException;
import meetona.shared.exception.InsertionFailedException;
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
public class MemberService implements IMemberService {

    private final MemberMapper mapper;
    private final UnitRepository unitRepository;
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final MemberActionProducer memberActionProducer;

    @Override
    @Cacheable("members")
    public ServiceResponse<List<MemberDto>> getAll(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);

        List<MemberDto> memberDto = members.stream()
                .map(mapper::toDto)
                .toList();

        ServiceResponse<List<MemberDto>> response = new ServiceResponse<>(memberDto, true);

        log.info("Fetched units => {}", memberDto);
        return response;
    }

    @Override
    @Cacheable("member")
    public ServiceResponse<MemberDto> getById(UUID id) {
        Optional<Member> memberOptional = memberRepository.findById(id);

        Member member = memberOptional.orElse(null); // Unwrap the Optional to get a Unit or null

        MemberDto memberDto = mapper.toDto(member);

        var response = new ServiceResponse<>(memberDto, true);

        log.info("Fetched unit => {}", memberDto);
        return response;
    }

    @Override
    @Cacheable("member")
    public ServiceResponse<MemberDto> getByEmail(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        Member member = memberOptional.orElse(null);

        MemberDto memberDto = mapper.toDto(member);

        var response = new ServiceResponse<>(memberDto, true);

        log.info("Fetched unit => {}", memberDto);
        return response;
    }

    @Override
    @Transactional
    public ServiceResponse<MemberDto> add(MemberRequest request) {
        boolean isEmailExists = memberRepository.existsByEmail(request.email());
        boolean isPhoneNumberExists = memberRepository.existsByPhoneNumber(request.phoneNumber());

        if (isEmailExists) {
            throw new InsertionFailedException(request.email(), " already exists");
        }

        if (isPhoneNumberExists) {
            throw new InsertionFailedException(request.phoneNumber(), " already exists");
        }

        Member newMember = buildMember(request);
        memberRepository.save(newMember);

        MemberDto unitDto = mapper.toDto(newMember);
        var response = new ServiceResponse<>(unitDto, true);

        memberActionProducer.sendMessage(unitDto);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "unit", key = "#unit.id")
    public ServiceResponse<MemberDto> update(UUID id, MemberRequest request) {
        boolean isUnitExists = memberRepository.existsById(id);

        if(!isUnitExists) {
            throw new AppException("Id does not exists");
        }

        Member newMember = buildMember(request);

        memberRepository.save(newMember);
        MemberDto updatedMember = mapper.toDto(newMember);

        var response = new ServiceResponse<>(updatedMember, true);

        memberActionProducer.sendMessage(id, updatedMember);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "unit", key = "#id")
    public ServiceResponse<MemberDto> delete(UUID id) {
        boolean isUnitExists = memberRepository.existsById(id);

        if(!isUnitExists){
            throw new ResourceNotFoundException("User", "id", id);
        }

        memberRepository.deleteById(id);
        MemberDto deletedMemberDto = new MemberDto(id, null, null, null, null, null, null, null, null, null, null);

        var response = new ServiceResponse<>(deletedMemberDto, true);

        memberActionProducer.sendMessage(id);
        return response;
    }

    private Member buildMember(MemberRequest request) {
        var unit = unitRepository
                .findById(request.unitId())
                .orElseThrow(() -> new IllegalArgumentException(request.unitId() + " does not exist"));

        Department department = null;
        UUID departmentId = request.departmentId();

        if(departmentId != null) {
            department = departmentRepository
                    .findById(departmentId)
                    .orElseThrow(() -> new IllegalArgumentException(request.departmentId() + " does not exist"));
        }

        return Member.builder()
                .firstName(request.firstName())
                .middleName(request.middleName())
                .lastName(request.lastName())
                .gender(request.gender())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .birthDate(request.birthDate())
                .maritalStatus(request.maritalStatus())
                .marriageDate(request.MarriageDate())
                .unit(unit)
                .department(department)
                .build();
    }
}
