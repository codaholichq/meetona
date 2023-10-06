package meetona.core.interfaces;

import meetona.core.payload.request.LoginDto;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.UserDto;

public interface IAuthService {
    ApiResponse<UserDto> authenticate(LoginDto loginDto);
}
