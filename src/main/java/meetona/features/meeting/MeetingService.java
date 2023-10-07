package meetona.features.meeting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.features.unit.UnitRepository;
import meetona.shared.exception.AppException;
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
public class MeetingService implements IMeetingService {

    private final MeetingMapper mapper;
    private final UnitRepository unitRepository;
    private final MeetingRepository meetingRepository;
//    private final MeetingActionProducer meetingActionProducer;

    @Override
    @Cacheable("meetings")
    public ApiResponse<List<MeetingDto>> getAll(Pageable pageable) {
        Page<Meeting> meetings = meetingRepository.findAll(pageable);

        List<MeetingDto> meetingDto = meetings.stream()
                .map(mapper::toDto)
                .toList();

        var response = new ApiResponse<>(meetingDto, true);

        log.info("Fetched units => {}", meetingDto);
        return response;
    }

    @Override
    @Cacheable("meeting")
    public ApiResponse<MeetingDto> getById(UUID id) {
        Meeting meeting = meetingRepository.findById(id).orElse(null);

        MeetingDto meetingDto = mapper.toDto(meeting);

        var response = new ApiResponse<>(meetingDto, true);

        log.info("Fetched unit => {}", meetingDto);
        return response;
    }

    @Override
    @Transactional
    public ApiResponse<MeetingDto> add(MeetingRequest request) {

        Meeting newMeeting = buildMeeting(request);
        meetingRepository.save(newMeeting);

        MeetingDto meetingDto = mapper.toDto(newMeeting);
        var response = new ApiResponse<>(meetingDto, true);

//        meetingActionProducer.sendMessage(meetingDto);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "meeting", key = "#meeting.id")
    public ApiResponse<MeetingDto> update(UUID id, MeetingRequest request) {
        boolean isMeetingExists = meetingRepository.existsById(id);

        if(isMeetingExists) {
            throw new AppException("Id does not exists");
        }

        Meeting newMeeting = buildMeeting(request);

        meetingRepository.save(newMeeting);
        MeetingDto updatedMeeting = mapper.toDto(newMeeting);

        var response = new ApiResponse<>(updatedMeeting, true);

//        meetingActionProducer.sendMessage(id, updatedMember);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "meeting", key = "#id")
    public ApiResponse<MeetingDto> delete(UUID id) {
        boolean isUnitExists = meetingRepository.existsById(id);

        if(isUnitExists){
            throw new AppException("Id does not exists");
        }

        meetingRepository.deleteById(id);
        MeetingDto deletedMeeting = new MeetingDto(id, 0, null, null);

        var response = new ApiResponse<>(deletedMeeting, true);

//        meetingActionProducer.sendMessage(id);
        return response;
    }

    private Meeting buildMeeting(MeetingRequest request) {
        var unit = unitRepository
                .findById(request.unitId())
                .orElseThrow(() -> new IllegalArgumentException(request.unitId() + " does not exist"));

        return Meeting.builder()
                .memberCount(request.memberCount())
                .prayerPoint(request.prayerPoint())
                .unit(unit)
                .build();
    }
}
