package meetona.core.interfaces;

import meetona.core.Dto.request.LoginDto;
import meetona.core.Dto.request.SignupDto;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.UserDto;

public interface IUserService {
    ApiResponse<UserDto> register(SignupDto signupDto);
    ApiResponse<UserDto> authenticate(LoginDto loginDto);
}
