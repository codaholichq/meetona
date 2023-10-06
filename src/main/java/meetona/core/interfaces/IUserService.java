package meetona.core.interfaces;

import meetona.core.payload.request.UserRequest;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.UserDto;
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
