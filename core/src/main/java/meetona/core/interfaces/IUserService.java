package meetona.core.interfaces;

import meetona.core.payload.request.LoginDto;
import meetona.core.payload.request.SignupDto;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.UserDto;

public interface IUserService {
    ApiResponse<UserDto> register(SignupDto signupDto);
    ApiResponse<UserDto> authenticate(LoginDto loginDto);
}
