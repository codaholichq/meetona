package meetona.core.interfaces;

import meetona.core.payload.request.AddUserDto;
import meetona.core.payload.response.ApiResponse;
import meetona.core.payload.response.UserDto;

public interface IUserService {
    ApiResponse<UserDto> add(AddUserDto addUserDto);
}
