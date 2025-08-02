package cz.rodro.dto.mapper;

import cz.rodro.dto.PersonDTO;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.PersonEntity;
import org.mapstruct.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
// import org.mapstruct.Named; // Only if you go with advanced @Context/Repository pattern for toEntity
// import org.mapstruct.Context; // Only if you go with advanced @Context/Repository pattern for toEntity

// Import your DTOs, Entities, and Enums
// import your.package.PersonDTO;
// import your.package.PersonEntity;
// import your.package.LocationEntity;
// import your.package.PersonOccupationEntity;
// import your.package.PersonSourceEvidenceEntity;
// import your.package.Gender; // etc.

// If you decide to use @Context for toEntity, you'd also import your repositories:
// import your.package.LocationRepository;
// import your.package.PersonRepository;

/**
 * MapStruct mapper interface for converting between PersonEntity and PersonDTO.
 * This mapper handles the transformation of core fields and delegates
 * complex relationship lookups (Entity creation from ID) to the service layer,
 * while mapping entity relationships to DTO IDs for efficient API responses.
 */
// Ensure you have LocationMapper.class (and other mappers like PersonOccupationMapper, PersonSourceEvidenceMapper)
// available and included in the 'uses' attribute.
// If your PersonMapper is an interface, you cannot @Autowired.
// If you want to @Autowired other mappers, PersonMapper needs to be an abstract class.
@Mapper(
        componentModel = "spring",
        uses = {LocationMapper.class /*, PersonOccupationMapper.class, PersonSourceEvidenceMapper.class */}, // <-- IMPORTANT: Add LocationMapper.class here
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE // Good for general updates
)
public abstract class PersonMapper { // <-- IMPORTANT: Change to abstract class if you need @Autowired

    @Autowired // Inject other mappers that this one 'uses'
    protected LocationMapper locationMapper;
    // @Autowired protected PersonOccupationMapper occupationMapper; // If you create one
    // @Autowired protected PersonSourceEvidenceMapper sourceEvidenceMapper; // If you create one

    // toEntity method: Correct as previously discussed (ignoring relationships)
    @Mapping(target = "birthPlace", ignore = true)
    @Mapping(target = "baptizationPlace", ignore = true)
    @Mapping(target = "deathPlace", ignore = true)
    @Mapping(target = "burialPlace", ignore = true)
    @Mapping(target = "mother", ignore = true)
    @Mapping(target = "father", ignore = true)
    @Mapping(target = "occupations", ignore = true)
    @Mapping(target = "sourceEvidences", ignore = true)
    public abstract PersonEntity toEntity(PersonDTO source); // Changed to abstract

    /**
     * Converts a PersonEntity to a PersonDTO.
     * It now maps the full LocationEntity objects to LocationDTO objects.
     */
    // REMOVE all these lines for places. MapStruct will automatically map LocationEntity to LocationDTO
    // because PersonDTO now has LocationDTO fields and you specified LocationMapper in 'uses'.
    // @Mapping(target = "birthPlaceId", source = "birthPlace.id") // <-- REMOVE THIS LINE
    // @Mapping(target = "baptizationPlaceId", source = "baptizationPlace.id") // <-- REMOVE THIS LINE
    // @Mapping(target = "deathPlaceId", source = "deathPlace.id") // <-- REMOVE THIS LINE
    // @Mapping(target = "burialPlaceId", source = "burialPlace.id") // <-- REMOVE THIS LINE

    // KEEP these if PersonDTO still has Long motherId/fatherId.
    // If PersonDTO also changed to PersonDTO mother; PersonDTO father; then remove these too.
    @Mapping(target = "motherId", source = "mother.id")
    @Mapping(target = "fatherId", source = "father.id")

    // MapStruct will handle these if you have mappers for them listed in 'uses'
    // e.g., PersonOccupationEntity -> PersonOccupationDTO, PersonSourceEvidenceEntity -> PersonSourceEvidenceDTO
    @Mapping(target = "occupations", source = "occupations")
    @Mapping(target = "sourceEvidences", source = "sourceEvidences")
    public abstract PersonDTO toDTO(PersonEntity source); // Changed to abstract

    /**
     * Updates an existing PersonEntity with data from a PersonDTO.
     * Relationships (places, mother, father, collections) are ignored here;
     * their updates are managed manually in the service layer to handle
     * database lookups for entities and collection synchronization.
     *
     * @param dto The PersonDTO containing the updated data.
     * @param entity The existing PersonEntity to update (target of the mapping).
     */
    // This method is already correct from previous discussions.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "birthPlace", ignore = true)
    @Mapping(target = "deathPlace", ignore = true)
    @Mapping(target = "burialPlace", ignore = true)
    @Mapping(target = "baptizationPlace", ignore = true)
    @Mapping(target = "mother", ignore = true)
    @Mapping(target = "father", ignore = true)
    @Mapping(target = "occupations", ignore = true)
    @Mapping(target = "sourceEvidences", ignore = true)
    public abstract void updatePersonEntity(PersonDTO dto, @MappingTarget PersonEntity entity); // Changed to abstract
}