package meetona.core.interfaces;

import meetona.core.Dto.request.LoginDto;
import meetona.core.Dto.request.SignupDto;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.AuthResponse;
import meetona.core.Dto.response.UserDto;

import java.util.concurrent.CompletableFuture;

public interface IUserService {
    CompletableFuture<ApiResponse<UserDto>> register(SignupDto signupDto);
    CompletableFuture<ApiResponse<UserDto>> authenticate(LoginDto loginDto);
}
