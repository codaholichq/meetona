package meetona.features.auth;

import meetona.features.user.UserDto;
import meetona.shared.response.ApiResponse;

public interface IAuthService {
    ApiResponse<UserDto> authenticate(LoginDto loginDto);
}
