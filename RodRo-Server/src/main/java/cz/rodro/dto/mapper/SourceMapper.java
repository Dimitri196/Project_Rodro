package cz.rodro.dto.mapper;

import cz.rodro.dto.SourceDTO;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.SourceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper interface for converting between {@link SourceEntity} and {@link SourceDTO}.
 * Handles mapping of basic fields and specifically addresses bidirectional relationships
 * to prevent StackOverflowError during JSON serialization.
 */
@Mapper(
        componentModel = "spring",
        // No 'uses' for LocationMapper here, as we are directly mapping location ID/name
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SourceMapper {

    /**
     * Converts a {@link SourceDTO} to a {@link SourceEntity}.
     * Maps sourceLocationId to sourceLocation (entity).
     * Note: The actual LocationEntity resolution by ID should ideally happen in the service layer
     * before saving, as MapStruct's direct mapping from ID to entity reference might not always
     * fetch the managed entity correctly in all contexts.
     *
     * @param source The SourceDTO to convert.
     * @return The converted SourceEntity.
     */
    @Mapping(target = "sourceLocation", source = "sourceLocationId") // Map ID to entity
    SourceEntity toSourceEntity(SourceDTO source);

    /**
     * Converts a {@link SourceEntity} to a {@link SourceDTO}.
     * Maps sourceLocation (entity) to sourceLocationId and sourceLocationName.
     * This explicitly breaks the recursion by not mapping the full LocationDTO object.
     *
     * @param source The SourceEntity to convert.
     * @return The converted SourceDTO.
     */
    @Mapping(target = "sourceLocationId", source = "sourceLocation.id")
    @Mapping(target = "sourceLocationName", source = "sourceLocation.locationName")
    // Removed: @Mapping(target = "sourceLocation", ignore = true) // This was causing the error as SourceDTO doesn't have 'sourceLocation'
    SourceDTO toSourceDTO(SourceEntity source);

    /**
     * Updates an existing {@link SourceEntity} with data from a {@link SourceDTO}.
     *
     * @param dto The SourceDTO containing updated data.
     * @param entity The target SourceEntity to update.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sourceLocation", source = "sourceLocationId") // Update sourceLocation based on ID
    void updateSourceEntity(SourceDTO dto, @MappingTarget SourceEntity entity);

    /**
     * Helper method for MapStruct to map a Long ID to a LocationEntity.
     * This method will be used internally by MapStruct when mapping sourceLocationId to sourceLocation.
     * It assumes that a LocationRepository is available to fetch or reference the LocationEntity.
     *
     * IMPORTANT: This method should ideally be implemented by a concrete mapper class or
     * in a separate helper class that MapStruct can use. For simplicity in the interface,
     * we're declaring it here. The actual implementation (e.g., using `locationRepository.getReferenceById(id)`)
     * would be provided by MapStruct's generated implementation or a custom mapper.
     *
     * For a Spring component model, you would typically inject the repository.
     * MapStruct will automatically generate an implementation for this method if
     * it can find a suitable bean.
     *
     * @param id The ID of the LocationEntity.
     * @return A reference to the LocationEntity with the given ID.
     */
    default LocationEntity map(Long id) {
        if (id == null) {
            return null;
        }
        // This is a placeholder. In a real application, you'd inject LocationRepository
        // and use locationRepository.getReferenceById(id) or findById(id).
        // MapStruct will try to auto-wire this.
        LocationEntity location = new LocationEntity();
        location.setId(id);
        return location;
    }
}
