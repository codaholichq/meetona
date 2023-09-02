package meetona.data.mapper;

import meetona.core.Dto.request.AddUnitDto;
import meetona.core.Dto.response.UnitDto;
import meetona.core.entity.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UnitMapper {
    UnitMapper MAPPER = Mappers.getMapper(UnitMapper.class);

    UnitDto ToUnitDto(Unit unit);
}
