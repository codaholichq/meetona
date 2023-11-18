package meetona.user;

import jakarta.validation.Valid;
import meetona.shared.response.ServiceResponse;
import meetona.user.dtos.AuthDto;
import meetona.user.dtos.UserDto;
import meetona.user.dtos.UserRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/auth",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<UserDto>> authenticate(@Valid @RequestBody AuthDto authDto) {
        return ResponseEntity.ok(userService.authenticate(authDto));
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<List<UserDto>>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<UserDto>> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping(value = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<UserDto>> add(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.add(request));
    }

    @PutMapping(
            value = "/user/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<UserDto>> update(
            @PathVariable("id") UUID id,
            @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<UserDto>> delete(@PathVariable("id") UUID id){
        return ResponseEntity.ok(userService.delete(id));
    }
}
