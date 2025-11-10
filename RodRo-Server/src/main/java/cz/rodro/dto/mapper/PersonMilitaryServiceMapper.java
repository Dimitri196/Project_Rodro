package cz.rodro.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.entity.PersonMilitaryServiceEntity;
import org.springframework.stereotype.Component;


/**
 * MapStruct mapper for converting PersonMilitaryServiceEntity to PersonMilitaryServiceDTO.
 * Critically uses Simple DTOs to avoid circular dependencies and denormalizes Person and Rank data.
 */
@Mapper(
        componentModel = "spring")
@Component
public interface PersonMilitaryServiceMapper {

    /**
     * Converts a PersonMilitaryServiceEntity to a PersonMilitaryServiceDTO, denormalizing key information.
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "personId", source = "person.id")
    @Mapping(target = "personGivenName", source = "person.givenName")
    @Mapping(target = "personSurname", source = "person.surname")
    @Mapping(target = "militaryStructureName", source = "militaryStructure.name")
    @Mapping(target = "armyBranchName", source = "militaryStructure.organization.armyBranch.name")
    @Mapping(target = "militaryRank", source = "militaryRank")
    @Mapping(target = "militaryStructure", source = "militaryStructure")
    PersonMilitaryServiceDTO toDto(PersonMilitaryServiceEntity entity);


   PersonMilitaryServiceEntity toEntity(PersonMilitaryServiceDTO dto);

    void updatePersonMilitaryServiceEntity(PersonMilitaryServiceDTO dto, @MappingTarget PersonMilitaryServiceEntity entity);
}
