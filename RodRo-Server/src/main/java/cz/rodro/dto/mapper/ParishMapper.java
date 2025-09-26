package cz.rodro.dto.mapper;

import cz.rodro.dto.ParishDTO;
import cz.rodro.entity.ParishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper interface for converting between {@link ParishEntity} and {@link ParishDTO}.
 * <p>
 * Uses MapStruct to automatically generate implementation at build time.
 * Handles flattening of relationships to avoid recursion or circular references.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface ParishMapper {

    /**
     * Converts a {@link ParishEntity} to {@link ParishDTO}.
     * Ignores the {@code parishLocation} field to prevent circular references.
     *
     * @param parishEntity The entity to convert.
     * @return The mapped {@link ParishDTO}.
     */
    @Mapping(target = "location", ignore = true)
    ParishDTO toParishDTO(ParishEntity parishEntity);

    /**
     * Converts a {@link ParishDTO} to {@link ParishEntity}.
     * <p>
     * Note: This method does not map nested {@link com.example.domain.ParishLocationEntity}
     * objects. Creating or updating parish locations should be handled in the service layer.
     * </p>
     *
     * @param parishDTO The DTO to convert.
     * @return The mapped {@link ParishEntity}.
     */
    ParishEntity toParishEntity(ParishDTO parishDTO);

    /**
     * Updates an existing {@link ParishEntity} with values from {@link ParishDTO}.
     * <p>
     * Only simple fields are updated. Relationships (parish locations) should be
     * handled separately in the service layer.
     * </p>
     *
     * @param parishDTO The DTO containing new values.
     * @param parishEntity The entity to update.
     */
    void updateParishEntity(ParishDTO parishDTO, @MappingTarget ParishEntity parishEntity);

}
