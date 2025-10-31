package cz.rodro.dto.mapper;

import cz.rodro.dto.PersonDTO;
import cz.rodro.entity.PersonEntity;
import org.mapstruct.*;


import java.util.List;

/**
 * Mapper for converting between PersonEntity and PersonDTO.
 * Supports nested mappings for locations, occupations, source evidences,
 * military services, and parent references by ID.
 */
@Mapper(
        componentModel = "spring",
        uses = { LocationMapper.class,
                PersonOccupationMapper.class,
                PersonSourceEvidenceMapper.class,
                PersonMilitaryServiceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PersonMapper {

    // ---------------------------
    // Entity -> DTO
    // ---------------------------
    @Mapping(target = "motherId", source = "mother.id")
    @Mapping(target = "fatherId", source = "father.id")
    PersonDTO toDTO(PersonEntity source);

    List<PersonDTO> toDTOList(List<PersonEntity> entities);

    // ---------------------------
    // DTO -> Entity (creation)
    // ---------------------------
    @Mapping(target = "mother", expression = "java(mapParent(dto.getMotherId()))")
    @Mapping(target = "father", expression = "java(mapParent(dto.getFatherId()))")
    @Mapping(target = "occupations", source = "occupations")
    @Mapping(target = "sourceEvidences", source = "sourceEvidences")
    PersonEntity toEntity(PersonDTO dto);

    // ---------------------------
    // DTO -> existing Entity (update)
    // ---------------------------
    @Mapping(target = "mother", expression = "java(mapParent(dto.getMotherId()))")
    @Mapping(target = "father", expression = "java(mapParent(dto.getFatherId()))")
    @Mapping(target = "occupations", source = "occupations")
    @Mapping(target = "sourceEvidences", source = "sourceEvidences")
    void updatePersonEntity(PersonDTO dto, @MappingTarget PersonEntity entity);

    // ---------------------------
    // Helper method to map parent ID to PersonEntity
    // ---------------------------
    default PersonEntity mapParent(Long parentId) {
        if (parentId == null) return null;
        PersonEntity parent = new PersonEntity();
        parent.setId(parentId);
        return parent;
    }
}
