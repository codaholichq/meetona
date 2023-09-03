package meetona.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.Dto.request.LoginDto;
import meetona.core.Dto.request.SignupDto;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.AuthResponse;
import meetona.core.Dto.response.UnitDto;
import meetona.core.Dto.response.UserDto;
import meetona.core.entity.User;
import meetona.core.event.SignedInEvent;
import meetona.core.event.SignedUpEvent;
import meetona.core.exception.LoginException;
import meetona.core.exception.SignupException;
import meetona.core.interfaces.IUserService;
import meetona.data.mapper.UserMapper;
import meetona.data.repository.UserRepository;
import meetona.data.security.TokenProvider;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
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
//                .unit(signupDto.unitId())
                .password(passwordEncoder.encode(signupDto.password()))
                .build();
    }

    public CompletableFuture<ApiResponse<UserDto>> register(SignupDto signupDto) {
        boolean isUsernameExists = userRepository.existsByUsername(signupDto.username());
        boolean isEmailExists = userRepository.existsByEmail(signupDto.email());

        if (isUsernameExists) {
            throw new SignupException(signupDto.username(), "Username already exists");
        }

        if (isEmailExists) {
            throw new SignupException(signupDto.email(), "Email already exists");
        }

        User newUser = createUser(signupDto);
        userRepository.save(newUser);
        eventPublisher.publishEvent(new SignedUpEvent(newUser));

        UserDto userDto = userMapper.ToUserDto(newUser);

        var response = new ApiResponse<>(userDto, true);
        return CompletableFuture.completedFuture(response);
    }

    public CompletableFuture<AuthResponse> authenticate(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.username());

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
