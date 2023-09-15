package meetona.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import meetona.core.payload.request.LoginDto;
import meetona.core.payload.request.SignupDto;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.UserDto;
import meetona.core.interfaces.IUserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;

    @PostMapping(value = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody SignupDto signupDto) {
        return ResponseEntity.ok(userService.register(signupDto));
    }

    @PostMapping(value = "login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserDto>> authenticate(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.authenticate(loginDto));
    }
}
