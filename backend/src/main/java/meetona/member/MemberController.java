package meetona.member;

import jakarta.validation.Valid;
import meetona.shared.response.ServiceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final IMemberService memberService;

    public MemberController(IMemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<List<MemberDto>>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(memberService.getAll(pageable));
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<MemberDto>> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(memberService.getById(id));
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<MemberDto>> getByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(memberService.getByEmail(email));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<MemberDto>> add(@Valid @RequestBody MemberRequest request) {
        return ResponseEntity.ok(memberService.add(request));
    }

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<MemberDto>> update(
            @PathVariable("id") UUID id,
            @RequestBody MemberRequest request
    ) {
        return ResponseEntity.ok(memberService.update(id, request));
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<MemberDto>> delete(@PathVariable("id") UUID id){
        return ResponseEntity.ok(memberService.delete(id));
    }
}
