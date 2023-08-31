package meetona.data.mapper;

import meetona.core.entity.User;
import meetona.data.Dto.request.LoginDto;
import meetona.data.Dto.request.SignupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    LoginDto toLoginDto(User user);

    @Mapping(target = "isEmailVerified", ignore = true)
    @Mapping(target = "units", ignore = true)
    User toUser(SignupDto signupDto);

    @Mapping(target = "isEmailVerified", ignore = true)
    @Mapping(target = "firstname", ignore = true)
    @Mapping(target = "lastname", ignore = true)
    @Mapping(target = "unitId", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "units", ignore = true)
    User toUser(LoginDto loginDto);
}
