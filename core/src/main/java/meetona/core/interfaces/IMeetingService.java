package meetona.core.interfaces;

import meetona.core.payload.request.MeetingRequest;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.MeetingDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IMeetingService {
    ApiResponse<List<MeetingDto>> getAll(Pageable pageable);
    ApiResponse<MeetingDto> getById(UUID id);
    ApiResponse<MeetingDto> add(MeetingRequest request);
    ApiResponse<MeetingDto> update(UUID id, MeetingRequest request);
    ApiResponse<MeetingDto> delete(UUID id);
}
