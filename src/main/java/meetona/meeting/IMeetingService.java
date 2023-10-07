package meetona.meeting;

import meetona.shared.response.ApiResponse;
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
