package meetona.web.controller;

import java.util.concurrent.CompletableFuture;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import meetona.core.Dto.request.LoginDto;
import meetona.core.Dto.request.SignupDto;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.AuthResponse;
import meetona.data.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<ApiResponse>> register(@Valid @RequestBody SignupDto signupDto) {
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
