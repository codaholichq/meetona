package meetona.features.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.features.user.User;
import meetona.features.user.UserActionProducer;
import meetona.features.user.UserDto;
import meetona.features.user.UserMapper;
import meetona.security.TokenProvider;
import meetona.shared.exception.LoginException;
import meetona.shared.response.ApiResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserMapper mapper;
    private final TokenProvider tokenProvider;
    private final UserActionProducer userActionProducer;
    private final AuthenticationManager authenticationManager;

    @Override
    public ApiResponse<UserDto> authenticate(LoginDto loginDto) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.username(),
                loginDto.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.createToken(authentication);

        User user = (User) authentication.getPrincipal();

        if (user == null) throw new LoginException("User not found");

        UserDto userDto = mapper.toDto(user).setAccessToken(accessToken);
        var response = new ApiResponse<>(userDto, true);

        userActionProducer.sendMessage(userDto);
        return response;
    }
}
