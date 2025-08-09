package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.entity.MilitaryOrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

// Ensure MilitaryArmyBranchMapper and CountryMapper are also used here
@Mapper(componentModel = "spring", uses = {MilitaryArmyBranchMapper.class, CountryMapper.class, MilitaryStructureSimpleMapper.class})
@Component
public interface MilitaryOrganizationMapper {

    @Mapping(target = "country.provinces", ignore = true)
    @Mapping(target = "structures", source = "structures")
    MilitaryOrganizationDTO toMilitaryOrganizationDTO(MilitaryOrganizationEntity entity);

    @Mapping(target = "country", ignore = true)
    @Mapping(target = "militaryRanks", ignore = true)
    @Mapping(target = "structures", source = "structures")
    MilitaryOrganizationEntity toMilitaryOrganizationEntity(MilitaryOrganizationDTO dto);

    void updateMilitaryOrganizationEntity(MilitaryOrganizationDTO dto, @MappingTarget MilitaryOrganizationEntity entity);

}
