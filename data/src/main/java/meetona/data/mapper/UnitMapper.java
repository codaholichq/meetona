package meetona.data.mapper;

import meetona.core.payload.response.UnitDto;
import meetona.core.entity.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UnitMapper {
    UnitMapper INSTANCE = Mappers.getMapper(UnitMapper.class);

    UnitDto ToUnitDto(Unit unit);

    @Mapping(target = "users", ignore = true)
    Unit ToUnit(UnitDto unitDto);
}
