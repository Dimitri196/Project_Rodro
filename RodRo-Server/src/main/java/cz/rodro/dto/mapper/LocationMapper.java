package cz.rodro.dto.mapper;

import cz.rodro.dto.LocationDTO;
import cz.rodro.entity.LocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper interface for converting between {@link LocationEntity} and {@link LocationDTO}.
 * Configured to use other mappers for nested DTOs/Entities and to ignore collection
 * fields during updates to allow for manual management in the service layer.
 */
@Mapper(
        componentModel = "spring",
        uses = { LocationHistoryMapper.class, SourceMapper.class, DistrictMapper.class }, // Added SourceMapper
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE // Ignore null values during mapping
)
public interface LocationMapper {

    /**
     * Converts a {@link LocationDTO} to a {@link LocationEntity}.
     * This method handles the conversion of basic fields and delegates
     * the mapping of nested collections (locationHistories, sources)
     * to their respective mappers.
     *
     * @param source The LocationDTO to convert.
     * @return The converted LocationEntity.
     */
    LocationEntity toLocationEntity(LocationDTO source);

    /**
     * Converts a {@link LocationEntity} to a {@link LocationDTO}.
     * This method handles the conversion of basic fields and delegates
     * the mapping of nested collections (locationHistories, sources)
     * to their respective mappers.
     *
     * @param source The LocationEntity to convert.
     * @return The converted LocationDTO.
     */
    LocationDTO toLocationDTO(LocationEntity source);

    /**
     * Updates an existing {@link LocationEntity} with data from a {@link LocationDTO}.
     * Collection fields (locationHistories, sources) are ignored during this update
     * as their lifecycle management (additions/removals) is typically handled
     * explicitly in the service layer to work correctly with JPA's cascade and orphanRemoval.
     *
     * @param dto The LocationDTO containing updated data.
     * @param entity The target LocationEntity to update.
     */
    @Mapping(target = "id", ignore = true) // ID should not be updated
    @Mapping(target = "locationHistories", ignore = true) // Collections usually managed in service layer for updates
    @Mapping(target = "sources", ignore = true) // Collections usually managed in service layer for updates
    @Mapping(target = "births", ignore = true) // Back-referenced collections should always be ignored
    @Mapping(target = "deaths", ignore = true) // Back-referenced collections should always be ignored
    @Mapping(target = "cemeteries", ignore = true) // Back-referenced collections should always be ignored
    @Mapping(target = "institutions", ignore = true) // Back-referenced collections should always be ignored
    @Mapping(target = "parishLocations", ignore = true) // Back-referenced collections should always be ignored
    void updateLocationEntity(LocationDTO dto, @MappingTarget LocationEntity entity);
}
