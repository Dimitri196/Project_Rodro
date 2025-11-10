package cz.rodro.dto.mapper;

import cz.rodro.dto.PersonOccupationDTO;
import cz.rodro.entity.PersonOccupationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonOccupationMapper {

    @Mapping(target = "personId", source = "person.id")
    @Mapping(target = "givenName", source = "person.givenName")
    @Mapping(target = "surname", source = "person.surname")
    @Mapping(target = "occupationId", source = "occupation.id")
    @Mapping(target = "startYear", source = "startYear")
    @Mapping(target = "endYear", source = "endYear")
    @Mapping(target = "occupationName", source = "occupation.occupationName")

    // Institution Name (Corrected in previous step)
    @Mapping(target = "institutionId", source = "occupation.institution.id")
    @Mapping(target = "institutionName", source = "occupation.institution.name")

    // Institution Location ID (Corrected in previous step)
    @Mapping(target = "institutionLocationId", source = "occupation.institution.location.id")

    // --- FINAL CORRECTION: Using 'locationName' field on LocationEntity ---
    @Mapping(target = "institutionLocationName", source = "occupation.institution.location.locationName")

    PersonOccupationDTO toDTO(PersonOccupationEntity entity);

    @Mapping(target = "person", ignore = true) // Relationship handled in service
    @Mapping(target = "occupation", ignore = true) // Relationship handled in service
    PersonOccupationEntity toEntity(PersonOccupationDTO dto);

    @Mapping(target = "id", ignore = true) // ID of relationship should not be updated
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "occupation", ignore = true)
    void updatePersonOccupationEntity(PersonOccupationDTO dto, @MappingTarget PersonOccupationEntity entity);

}