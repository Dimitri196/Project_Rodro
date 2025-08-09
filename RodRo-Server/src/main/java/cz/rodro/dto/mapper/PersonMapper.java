package cz.rodro.dto.mapper;

import cz.rodro.dto.PersonDTO;
import cz.rodro.entity.PersonEntity;
import org.mapstruct.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(
        componentModel = "spring",
        uses = { LocationMapper.class }, // Make sure LocationMapper is included
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PersonMapper {

    @Mapping(target = "motherId", source = "mother.id")
    @Mapping(target = "fatherId", source = "father.id")
    PersonDTO toDTO(PersonEntity source);

    @Mapping(target = "mother", ignore = true)
    @Mapping(target = "father", ignore = true)
    @Mapping(target = "occupations", ignore = true)
    @Mapping(target = "sourceEvidences", ignore = true)
    PersonEntity toEntity(PersonDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mother", ignore = true)
    @Mapping(target = "father", ignore = true)
    @Mapping(target = "occupations", ignore = true)
    @Mapping(target = "sourceEvidences", ignore = true)
    void updatePersonEntity(PersonDTO dto, @MappingTarget PersonEntity entity);
}
