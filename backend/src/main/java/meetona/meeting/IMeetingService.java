package meetona.meeting;

import meetona.shared.response.ServiceResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IMeetingService {
    ServiceResponse<List<MeetingDto>> getAll(Pageable pageable);
    ServiceResponse<MeetingDto> getById(UUID id);
    ServiceResponse<MeetingDto> add(MeetingRequest request);
    ServiceResponse<MeetingDto> update(UUID id, MeetingRequest request);
    ServiceResponse<MeetingDto> delete(UUID id);
}
