package meetona.core.interfaces;

import meetona.core.Dto.request.LoginDto;
import meetona.core.Dto.request.SignupDto;
import meetona.core.Dto.response.ApiResponse;
import meetona.core.Dto.response.AuthResponse;

import java.util.concurrent.CompletableFuture;

public interface IUserService {
    CompletableFuture<ApiResponse> register(SignupDto signupDto);
    CompletableFuture<AuthResponse> authenticate(LoginDto loginDto);
}
