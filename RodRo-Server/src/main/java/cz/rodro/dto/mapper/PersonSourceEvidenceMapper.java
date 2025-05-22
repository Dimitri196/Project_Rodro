package cz.rodro.dto.mapper;

import cz.rodro.dto.PersonSourceEvidenceDTO;
import cz.rodro.entity.PersonSourceEvidenceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {SourceMapper.class})
public interface PersonSourceEvidenceMapper {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "source.id", target = "sourceId")
    @Mapping(source = "personName", target = "personName")
    @Mapping(source = "sourceName", target = "sourceName")
    PersonSourceEvidenceDTO toDTO(PersonSourceEvidenceEntity entity);

    @Mapping(target = "person", ignore = true)
    @Mapping(target = "source", ignore = true)
    PersonSourceEvidenceEntity toEntity(PersonSourceEvidenceDTO dto);

    void updatePersonSourceEvidenceEntity(PersonSourceEvidenceDTO dto, @MappingTarget PersonSourceEvidenceEntity entity);
}