package cz.rodro.dto.mapper;

import cz.rodro.dto.CountryDTO;
import cz.rodro.entity.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper interface responsible for converting data between
 * the CountryEntity (JPA persistence model) and the CountryDTO (API data transfer model).
 *
 * @implNote The {@code componentModel = "spring"} setting makes the mapper
 * injectable as a Spring Bean, allowing for dependency injection.
 */
@Mapper(componentModel = "spring")
public interface CountryMapper {

    /**
     * Converts a single CountryEntity (database model) to a CountryDTO (API response model).
     *
     * @param countryEntity The entity retrieved from the database.
     * @return The resulting DTO, ready for transfer over the network.
     * @apiNote Provinces are explicitly ignored here to prevent potential circular references
     * and to ensure fast serialization.
     */
    @Mapping(target = "provinces", ignore = true)
    CountryDTO toCountryDTO(CountryEntity countryEntity);

    /**
     * Converts a list of CountryEntity objects to a list of CountryDTO objects.
     * This is typically used for `findAll` or paginated results.
     *
     * @param countryEntities The list of entities retrieved from the database.
     * @return The resulting list of DTOs.
     */
    List<CountryDTO> toCountryDTOs(List<CountryEntity> countryEntities);

    /**
     * Converts a CountryDTO (received from API request) to a new CountryEntity (persistence model).
     *
     * @param countryDTO The DTO received from the client.
     * @return The new entity, ready to be saved in the database.
     */
    CountryEntity toCountryEntity(CountryDTO countryDTO);

    /**
     * Updates an existing CountryEntity with data from a CountryDTO.
     * This method is crucial for handling PATCH/PUT operations where the ID must be preserved.
     *
     * @param countryDTO The source DTO containing the new data.
     * @param countryEntity The target entity instance to be updated.
     * @implSpec The ID and the related collections (provinces, organizations) are preserved
     * by default, preventing unwanted overwrites.
     */
    @Mapping(target = "provinces", ignore = true) // Preserve existing province relationships unless mapped explicitly
    @Mapping(target = "militaryOrganizations", ignore = true) // Preserve existing organization relationships
    void updateCountryEntityFromDTO(CountryDTO countryDTO, @MappingTarget CountryEntity countryEntity);
}
