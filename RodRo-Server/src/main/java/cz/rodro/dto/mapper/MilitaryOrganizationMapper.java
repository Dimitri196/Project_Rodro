package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.entity.MilitaryOrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        CountryMapper.class,
        MilitaryArmyBranchMapper.class,
        MilitaryStructureMapper.class
})
public interface MilitaryOrganizationMapper {

    @Mapping(target = "country.provinces", ignore = true)
    MilitaryOrganizationDTO toMilitaryOrganizationDTO(MilitaryOrganizationEntity entity);

    MilitaryOrganizationEntity toMilitaryOrganizationEntity(MilitaryOrganizationDTO dto);

    void updateMilitaryOrganizationEntity(MilitaryOrganizationDTO dto, @MappingTarget MilitaryOrganizationEntity entity);
}