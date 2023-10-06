package meetona.features.common;

import meetona.features.department.Department;
import meetona.features.department.DepartmentDto;
import meetona.features.meeting.Meeting;
import meetona.features.meeting.MeetingDto;
import meetona.features.member.Member;
import meetona.features.member.MemberDto;
import meetona.features.role.Role;
import meetona.features.role.RoleDto;
import meetona.features.unit.Unit;
import meetona.features.unit.UnitDto;
import meetona.features.user.User;
import meetona.features.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
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
