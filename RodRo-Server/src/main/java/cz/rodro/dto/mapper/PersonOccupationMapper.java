package cz.rodro.dto.mapper;

import cz.rodro.dto.PersonOccupationDTO;
import cz.rodro.entity.PersonOccupationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonOccupationMapper {

    @Mapping(target = "personId", source = "person.id")
    @Mapping(target = "givenName", source = "person.givenName")
    @Mapping(target = "givenSurname", source = "person.givenSurname")
    @Mapping(target = "occupationId", source = "occupation.id")
    @Mapping(target = "startDate", source = "occupationStartDate")
    @Mapping(target = "endDate", source = "occupationEndDate")
    @Mapping(target = "occupationName", source = "occupation.occupationName")
    @Mapping(target = "institutionId", source = "occupation.institution.id")
    @Mapping(target = "institutionName", source = "occupation.institution.institutionName")
    @Mapping(target = "institutionLocationId", source = "occupation.institution.institutionLocation.id")
    @Mapping(target = "institutionLocationName", source = "occupation.institution.institutionLocation.locationName")
    PersonOccupationDTO toDTO(PersonOccupationEntity entity);

}