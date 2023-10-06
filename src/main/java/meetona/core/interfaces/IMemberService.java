package meetona.core.interfaces;

import meetona.core.payload.request.MemberRequest;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.MemberDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IMemberService {
    ApiResponse<List<MemberDto>> getAll(Pageable pageable);
    ApiResponse<MemberDto> getById(UUID id);
    ApiResponse<MemberDto> add(MemberRequest request);
    ApiResponse<MemberDto> update(UUID id, MemberRequest request);
    ApiResponse<MemberDto> delete(UUID id);
}
