package meetona.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.entity.User;
import meetona.core.exception.LoginException;
import meetona.core.interfaces.IAuthService;
import meetona.core.payload.request.LoginDto;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.UserDto;
import meetona.data.mapper.GeneralMapper;
import meetona.data.messaging.producers.UserActionProducer;
import meetona.data.security.TokenProvider;
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

    private final GeneralMapper mapper;
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
