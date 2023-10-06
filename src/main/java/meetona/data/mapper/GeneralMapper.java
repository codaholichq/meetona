package meetona.data.mapper;

import meetona.core.entity.*;
import meetona.core.payload.response.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GeneralMapper {

    GeneralMapper INSTANCE = Mappers.getMapper(GeneralMapper.class);

    UnitDto toDto(Unit unit);
    UserDto toDto(User user);
    RoleDto toDto(Role role);
    MemberDto toDto(Member member);
    MeetingDto toDto(Meeting meeting);
    DepartmentDto toDto(Department department);

    Unit ToEntity(UnitDto unitDto);
    Department toEntity(DepartmentDto departmentDto);

    default List<String> mapRoles(Collection<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());
    }

    default String mapUnit(Unit unit) {
        return unit.getName();
    }
}
