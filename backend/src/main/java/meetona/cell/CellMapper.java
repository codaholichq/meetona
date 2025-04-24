package meetona.cell;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CellMapper {

    CellMapper INSTANCE = Mappers.getMapper(CellMapper.class);

    CellDto toDto(Cell cell);

    default String mapUnit(Cell cell) {
        return cell.getName();
    }

}
