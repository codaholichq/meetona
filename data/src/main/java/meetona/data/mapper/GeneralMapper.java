package meetona.data.mapper;

import meetona.core.entity.*;
import meetona.core.payload.response.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GeneralMapper {

    GeneralMapper INSTANCE = Mappers.getMapper(GeneralMapper.class);

    UnitDto toDto(Unit unit);
    UserDto toDto(User user);
    MemberDto toDto(Member member);
    MeetingDto toDto(Meeting meeting);
    DepartmentDto toDto(Department department);

    Unit ToEntity(UnitDto unitDto);
    Department toEntity(DepartmentDto departmentDto);

}
