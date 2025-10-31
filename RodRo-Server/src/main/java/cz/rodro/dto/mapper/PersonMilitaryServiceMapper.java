package cz.rodro.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.entity.PersonMilitaryServiceEntity;


@Mapper(componentModel = "spring", uses = {
        CountryMapper.class,
        MilitaryRankMapper.class,
        MilitaryStructureMapper.class,
        MilitaryOrganizationMapper.class
})
public interface PersonMilitaryServiceMapper {

    @Mapping(target = "personId", source = "person.id")
    @Mapping(target = "rankName", source = "militaryRank.rankName")
    @Mapping(target = "givenName", source = "person.givenName")
    @Mapping(target = "surname", source = "person.surname")
    PersonMilitaryServiceDTO toDto(PersonMilitaryServiceEntity entity);

    PersonMilitaryServiceEntity toEntity(PersonMilitaryServiceDTO dto);

    void updatePersonMilitaryServiceEntity(PersonMilitaryServiceDTO dto, @MappingTarget PersonMilitaryServiceEntity entity);
}