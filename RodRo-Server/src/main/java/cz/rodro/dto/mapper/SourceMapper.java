package cz.rodro.dto.mapper;

import cz.rodro.dto.SourceDTO;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.SourceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Mapper interface for converting between {@link SourceEntity} and {@link SourceDTO}.
 *
 * <p>Uses MapStruct to generate the implementation automatically at build time.
 * Takes care of flattening relationships (e.g., Location → ID & name) to avoid recursion.</p>
 */
@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SourceMapper {

    /**
     * Converts a {@link SourceDTO} to a {@link SourceEntity}.
     * <p>Maps {@code locationId} to a stub {@link LocationEntity}.
     * The service layer should replace this stub with a managed entity before persisting.</p>
     */
    @Mapping(target = "location", source = "locationId")
    @Mapping(target = "evidences", ignore = true)
    SourceEntity toSourceEntity(SourceDTO source);

    /**
     * Converts a {@link SourceEntity} to a {@link SourceDTO}.
     * Breaks recursion by only mapping the location ID and name instead of the full LocationEntity.
     */
    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "locationName", source = "location.locationName")
    SourceDTO toSourceDTO(SourceEntity source);

    /**
     * Updates an existing {@link SourceEntity} with values from {@link SourceDTO}.
     * <p>Does not update the entity ID.</p>
     *
     * @param dto    The DTO containing new values.
     * @param entity The entity to update.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location", ignore = true) // safer: update location explicitly in service
    @Mapping(target = "evidences", ignore = true)
    void updateSourceEntity(SourceDTO dto, @MappingTarget SourceEntity entity);

    /**
     * Maps a location ID to a stub {@link LocationEntity}.
     * Prevents full fetch unless service resolves it explicitly.
     */
    default LocationEntity map(Long id) {
        if (id == null) {
            return null;
        }
        LocationEntity location = new LocationEntity();
        location.setId(id);
        return location;
    }

    /**
     * Converts a list of {@link SourceEntity} to a list of {@link SourceDTO}.
     */
    List<SourceDTO> toSourceDTOList(List<SourceEntity> sources);
}
