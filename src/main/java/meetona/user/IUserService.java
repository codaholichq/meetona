package meetona.user;

import meetona.shared.response.ApiResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    ApiResponse<List<UserDto>> getAll(Pageable pageable);
    ApiResponse<UserDto> getById(UUID id);
    ApiResponse<UserDto> add(UserRequest request);
    ApiResponse<UserDto> update(UUID id, UserRequest request);
    ApiResponse<UserDto> delete(UUID id);
}
