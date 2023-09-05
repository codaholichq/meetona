package meetona.data.mapper;

import meetona.core.Dto.request.LoginDto;
import meetona.core.Dto.request.SignupDto;
import meetona.core.Dto.response.UserDto;
import meetona.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    LoginDto toLoginDto(User user);

    UserDto ToUserDto(User user);

    @Mapping(target = "isEmailVerified", ignore = true)
    @Mapping(target = "unit", ignore = true)
    User toUser(SignupDto signupDto);

    @Mapping(target = "isEmailVerified", ignore = true)
    @Mapping(target = "firstname", ignore = true)
    @Mapping(target = "lastname", ignore = true)
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(LoginDto loginDto);
}
