package meetona.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.payload.request.AddUserDto;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.UserDto;
import meetona.core.entity.User;
import meetona.core.exception.SignupException;
import meetona.core.interfaces.IUserService;
import meetona.data.mapper.UserMapper;
import meetona.data.messaging.producers.UserActionProducer;
import meetona.data.repository.UserRepository;
import meetona.data.security.TokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final UserMapper userMapper;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserActionProducer userActionProducer;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public ApiResponse<UserDto> add(AddUserDto addUserDto) {
        boolean isUsernameExists = userRepository.existsByUsername(addUserDto.username());
        boolean isEmailExists = userRepository.existsByEmail(addUserDto.email());

        if (isUsernameExists) {
            throw new SignupException(addUserDto.username(), "Username already exists");
        }

        if (isEmailExists) {
            throw new SignupException(addUserDto.email(), "Email already exists");
        }

        User newUser = createUser(addUserDto);
        userRepository.save(newUser);

        UserDto userDto = userMapper.ToUserDto(newUser);

        var response = new ApiResponse<>(userDto, true);

        userActionProducer.sendMessage(newUser);
        return response;
    }

    private User createUser(AddUserDto addUserDto) {
        return User.builder()
                .firstname(addUserDto.firstname())
                .lastname(addUserDto.lastname())
                .email(addUserDto.email())
                .username(addUserDto.username())
                .roles(addUserDto.roles())
                .isEmailVerified(false)
//                .unit(signupDto.unitId())
                .password(passwordEncoder.encode(addUserDto.password()))
                .build();
    }
}
