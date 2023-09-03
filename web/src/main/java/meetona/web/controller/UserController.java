package meetona.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import meetona.core.Dto.request.LoginDto;
import meetona.core.Dto.request.SignupDto;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.AuthResponse;
import meetona.core.Dto.response.UserDto;
import meetona.core.interfaces.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<ApiResponse<UserDto>>> register(@Valid @RequestBody SignupDto signupDto) {
        return userService
                .register(signupDto)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<AuthResponse>> authenticate(@Valid @RequestBody LoginDto loginDto) {
        return userService
                .authenticate(loginDto)
                .thenApply(ResponseEntity::ok);
    }
}
