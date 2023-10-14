package meetona.user;

import meetona.shared.response.ApiResponse;
import meetona.user.dtos.AuthDto;
import meetona.user.dtos.UserDto;
import meetona.user.dtos.UserRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    ApiResponse<UserDto> authenticate(AuthDto authDto);
    ApiResponse<List<UserDto>> getAll(Pageable pageable);
    ApiResponse<UserDto> getById(UUID id);
    ApiResponse<UserDto> add(UserRequest request);
    ApiResponse<UserDto> update(UUID id, UserRequest request);
    ApiResponse<UserDto> delete(UUID id);
}
