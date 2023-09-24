package meetona.data.mapper;

import meetona.core.entity.Department;
import meetona.core.entity.Member;
import meetona.core.entity.Unit;
import meetona.core.entity.User;
import meetona.core.payload.response.DepartmentDto;
import meetona.core.payload.response.MemberDto;
import meetona.core.payload.response.UnitDto;
import meetona.core.payload.response.UserDto;
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
    DepartmentDto toDto(Department department);

    Unit ToEntity(UnitDto unitDto);
    Department toEntity(DepartmentDto departmentDto);

}
