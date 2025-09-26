package cz.rodro.dto.mapper;

import cz.rodro.dto.LocationDTO;
import cz.rodro.entity.LocationEntity;
import org.mapstruct.*;

/**
 * Mapper for converting between {@link LocationEntity} and {@link LocationDTO}.
 * <p>
 * Collections and back-references are ignored during updates to avoid JPA cascade/orphanRemoval issues.
 * </p>
 */
@Mapper(
        componentModel = "spring",
        uses = { LocationHistoryMapper.class, SourceMapper.class, DistrictMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LocationMapper {

    /**
     * Maps a DTO to a full entity.
     */
    LocationEntity toLocationEntity(LocationDTO dto);

    /**
     * Maps a full entity to a DTO.
     */
    LocationDTO toLocationDTO(LocationEntity entity);

    /**
     * Updates an existing entity from a DTO.
     * Collection fields and back-references are ignored to prevent accidental overwrites.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "locationHistories", ignore = true)
    @Mapping(target = "sources", ignore = true)
    @Mapping(target = "births", ignore = true)
    @Mapping(target = "deaths", ignore = true)
    @Mapping(target = "parishLocations", ignore = true)
    @Mapping(target = "cemeteries", ignore = true)
    @Mapping(target = "institutions", ignore = true)
    void updateLocationEntity(LocationDTO dto, @MappingTarget LocationEntity entity);
}
