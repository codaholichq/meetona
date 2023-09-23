package meetona.data.mapper;

import meetona.core.payload.request.LoginDto;
import meetona.core.payload.request.UserRequest;
import meetona.core.payload.response.UserDto;
import meetona.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    LoginDto toLoginDto(User user);

    @Mapping(target = "accessToken", ignore = true)
    UserDto ToUserDto(User user);

    @Mapping(target = "isEmailVerified", ignore = true)
    @Mapping(target = "member", ignore = true)
    User toUser(UserRequest userRequest);

    @Mapping(target = "isEmailVerified", ignore = true)
    @Mapping(target = "member", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(LoginDto loginDto);
}
