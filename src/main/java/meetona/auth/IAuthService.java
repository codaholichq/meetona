package meetona.auth;

import meetona.user.UserDto;
import meetona.shared.response.ApiResponse;

public interface IAuthService {
    ApiResponse<UserDto> authenticate(LoginDto loginDto);
}
