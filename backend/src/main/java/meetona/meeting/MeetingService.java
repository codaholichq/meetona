package meetona.meeting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.shared.exception.ResourceNotFoundException;
import meetona.cell.CellRepository;
import meetona.shared.exception.AppException;
import meetona.shared.response.ServiceResponse;
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
public class MeetingService implements IMeetingService {

    private final MeetingMapper mapper;
    private final CellRepository cellRepository;
    private final MeetingRepository meetingRepository;
//    private final MeetingActionProducer meetingActionProducer;

    @Override
    @Cacheable("meetings")
    public ServiceResponse<List<MeetingDto>> getAll(Pageable pageable) {
        Page<Meeting> meetings = meetingRepository.findAll(pageable);

        List<MeetingDto> meetingDto = meetings.stream()
                .map(mapper::toDto)
                .toList();

        var response = new ServiceResponse<>(meetingDto, true);

        log.info("Fetched units => {}", meetingDto);
        return response;
    }

    @Override
    @Cacheable("meeting")
    public ServiceResponse<MeetingDto> getById(UUID id) {
        Meeting meeting = meetingRepository.findById(id).orElse(null);

        MeetingDto meetingDto = mapper.toDto(meeting);

        var response = new ServiceResponse<>(meetingDto, true);

        log.info("Fetched cell => {}", meetingDto);
        return response;
    }

    @Override
    @Transactional
    public ServiceResponse<MeetingDto> add(MeetingRequest request) {

        Meeting newMeeting = buildMeeting(request);
        meetingRepository.save(newMeeting);

        MeetingDto meetingDto = mapper.toDto(newMeeting);
        var response = new ServiceResponse<>(meetingDto, true);

//        meetingActionProducer.sendMessage(meetingDto);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "meeting", key = "#meeting.id")
    public ServiceResponse<MeetingDto> update(UUID id, MeetingRequest request) {
        boolean isMeetingExists = meetingRepository.existsById(id);

        if(!isMeetingExists) {
            throw new AppException("Id does not exists");
        }

        Meeting newMeeting = buildMeeting(request);

        meetingRepository.save(newMeeting);
        MeetingDto updatedMeeting = mapper.toDto(newMeeting);

        var response = new ServiceResponse<>(updatedMeeting, true);

//        meetingActionProducer.sendMessage(id, updatedMember);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "meeting", key = "#id")
    public ServiceResponse<MeetingDto> delete(UUID id) {
        boolean isUnitExists = meetingRepository.existsById(id);

        if(!isUnitExists){
            throw new ResourceNotFoundException("User", "id", id);
        }

        meetingRepository.deleteById(id);
        MeetingDto deletedMeeting = new MeetingDto(id, 0, null, null);

        var response = new ServiceResponse<>(deletedMeeting, true);

//        meetingActionProducer.sendMessage(id);
        return response;
    }

    private Meeting buildMeeting(MeetingRequest request) {
        var cell = cellRepository
                .findById(request.cellId())
                .orElseThrow(() -> new IllegalArgumentException(request.cellId() + " does not exist"));

        return Meeting.builder()
                .memberCount(request.memberCount())
                .prayerPoint(request.prayerPoint())
                .cell(cell)
                .build();
    }
}
