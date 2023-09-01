package meetona.data.repository;

import meetona.data.Dto.request.LoginDto;
import meetona.data.Dto.request.SignupDto;
import meetona.data.Dto.response.ApiResponse;
import meetona.data.Dto.response.AuthResponse;

import java.util.concurrent.CompletableFuture;

public interface IUserService {
    CompletableFuture<ApiResponse> register(SignupDto signupDto);
    CompletableFuture<AuthResponse> authenticate(LoginDto loginDto);
}
