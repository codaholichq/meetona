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
@RequestMapping(value = "/api/user",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final IUserService userService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody SignupDto signupDto) {
        return ResponseEntity.ok(userService.register(signupDto));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<UserDto>> authenticate(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.authenticate(loginDto));
    }
}
