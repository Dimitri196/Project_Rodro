package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryArmyBranchDTO;
import cz.rodro.entity.MilitaryArmyBranchEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MilitaryArmyBranchMapper {

    MilitaryArmyBranchDTO toDto(MilitaryArmyBranchEntity entity);

    MilitaryArmyBranchEntity toEntity(MilitaryArmyBranchDTO dto);
}
