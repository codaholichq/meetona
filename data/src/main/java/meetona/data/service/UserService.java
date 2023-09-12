package meetona.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.Dto.request.LoginDto;
import meetona.core.Dto.request.SignupDto;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.UserDto;
import meetona.core.entity.User;
import meetona.core.exception.LoginException;
import meetona.core.exception.SignupException;
import meetona.core.interfaces.IUserService;
import meetona.data.mapper.UserMapper;
import meetona.data.repository.UserRepository;
import meetona.data.security.TokenProvider;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public ApiResponse<UserDto> register(SignupDto signupDto) {
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

//        eventPublisher.publishEvent(new SignedUpEvent(newUser));

        UserDto userDto = userMapper.ToUserDto(newUser);

        var response = new ApiResponse<>(userDto, true);
        return response;
    }

    public ApiResponse<UserDto> authenticate(LoginDto loginDto) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.username(),
                loginDto.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.createToken(authentication);

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new LoginException("User not found");
        }

        UserDto userDto = userMapper.ToUserDto(user).setAccessToken(accessToken);
        var response = new ApiResponse<>(userDto, true);

        return response;
    }

    private User createUser(SignupDto signupDto) {
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
}
