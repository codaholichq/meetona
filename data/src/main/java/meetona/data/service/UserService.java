package meetona.data.service;

import meetona.data.Dto.request.LoginDto;
import meetona.data.Dto.request.SignupDto;
import meetona.data.Dto.response.ApiResponse;
import meetona.data.Dto.response.AuthResponse;
import meetona.core.event.SignedInEvent;
import meetona.core.event.SignedUpEvent;
import meetona.core.exception.LoginException;
import meetona.core.exception.SignupException;
import meetona.core.entity.User;
import meetona.data.repository.UserRepository;
import meetona.data.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public User createUser(SignupDto signupDto) {

        return User.builder()
                .firstname(signupDto.firstname())
                .lastname(signupDto.lastname())
                .email(signupDto.email())
                .username(signupDto.username())
                .roles(signupDto.roles())
                .isEmailVerified(false)
                .unitId(signupDto.unitId())
                .password(passwordEncoder.encode(signupDto.password()))
                .build();
    }

    public CompletableFuture<ApiResponse> register(SignupDto signupDto) {
        boolean isUsernameExists = repository.existsByUsername(signupDto.username());
        boolean isEmailExists = repository.existsByEmail(signupDto.email());

        if (isUsernameExists) {
            throw new SignupException(signupDto.username(), "Username already exists");
        }

        if (isEmailExists) {
            throw new SignupException(signupDto.email(), "Email already exists");
        }

        User newUser = createUser(signupDto);
        repository.save(newUser);
        eventPublisher.publishEvent(new SignedUpEvent(newUser));
        return CompletableFuture.completedFuture(new ApiResponse("User registered successfully", true));
    }

    public CompletableFuture<AuthResponse> authenticate(LoginDto loginDto) {
        User user = repository.findByUsername(loginDto.username());

        if (user == null) {
            throw new LoginException("User not found");
        }

        if (passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            String accessToken = tokenProvider.generateToken(user);
            Long expiryDuration = tokenProvider.getExpiryDuration();

            eventPublisher.publishEvent(new SignedInEvent(user));

            return CompletableFuture.completedFuture(new AuthResponse(accessToken, expiryDuration));
        } else {
            throw new LoginException("Incorrect username or password");
        }
    }


}
