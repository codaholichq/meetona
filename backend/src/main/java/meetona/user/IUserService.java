package meetona.user;

import meetona.shared.response.ServiceResponse;
import meetona.user.dtos.AuthDto;
import meetona.user.dtos.UserDto;
import meetona.user.dtos.UserRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    ServiceResponse<UserDto> authenticate(AuthDto authDto);
    ServiceResponse<List<UserDto>> getAll(Pageable pageable);
    ServiceResponse<UserDto> getById(UUID id);
    ServiceResponse<UserDto> add(UserRequest request);
    ServiceResponse<UserDto> update(UUID id, UserRequest request);
    ServiceResponse<UserDto> delete(UUID id);
}
