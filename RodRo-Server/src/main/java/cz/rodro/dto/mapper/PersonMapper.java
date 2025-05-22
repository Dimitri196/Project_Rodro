package cz.rodro.dto.mapper;

import cz.rodro.dto.PersonDTO;
import cz.rodro.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "birthPlace", source = "birthPlace")
    @Mapping(target = "deathPlace", source = "deathPlace")
    @Mapping(target = "burialPlace", source = "burialPlace")
    @Mapping(target = "baptizationPlace", source = "baptizationPlace")
    @Mapping(target = "mother", source = "mother")
    @Mapping(target = "father", source = "father")
    @Mapping(target = "socialStatus", source = "socialStatus")
    @Mapping(target = "occupations", ignore = true) // handled manually
    PersonEntity toEntity(PersonDTO source);

    @Mapping(target = "occupations", source = "occupations")
    @Mapping(target = "sourceEvidences", source = "sourceEvidences")
    PersonDTO toDTO(PersonEntity source);

    @Mapping(target = "birthPlace", ignore = true)
    @Mapping(target = "deathPlace", ignore = true)
    @Mapping(target = "burialPlace", ignore = true)
    @Mapping(target = "baptizationPlace", ignore = true)
    @Mapping(target = "occupations", ignore = true)
    @Mapping(target = "sourceEvidences", ignore = true)
    void updatePersonEntity(PersonDTO dto, @MappingTarget PersonEntity entity);
}