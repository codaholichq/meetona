package meetona.meeting;

import jakarta.validation.Valid;
import meetona.shared.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

    private final IMeetingService meetingService;

    public MeetingController(IMeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<MeetingDto>>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(meetingService.getAll(pageable));
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<MeetingDto>> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(meetingService.getById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<MeetingDto>> add(@Valid @RequestBody MeetingRequest request) {
        return ResponseEntity.ok(meetingService.add(request));
    }

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<MeetingDto>> update(
            @PathVariable("id") UUID id,
            @RequestBody MeetingRequest request
    ) {
        return ResponseEntity.ok(meetingService.update(id, request));
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<MeetingDto>> delete(@PathVariable("id") UUID id){
        return ResponseEntity.ok(meetingService.delete(id));
    }
}
