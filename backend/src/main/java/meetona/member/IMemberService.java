package meetona.member;

import meetona.shared.response.ServiceResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IMemberService {
    ServiceResponse<List<MemberDto>> getAll(Pageable pageable);
    ServiceResponse<MemberDto> getById(UUID id);
    ServiceResponse<MemberDto> getByEmail(String email);
    ServiceResponse<MemberDto> add(MemberRequest request);
    ServiceResponse<MemberDto> update(UUID id, MemberRequest request);
    ServiceResponse<MemberDto> delete(UUID id);
}
