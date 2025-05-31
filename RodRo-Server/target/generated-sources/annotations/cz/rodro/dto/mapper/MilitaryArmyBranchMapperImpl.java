package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryArmyBranchDTO;
import cz.rodro.entity.MilitaryArmyBranchEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class MilitaryArmyBranchMapperImpl implements MilitaryArmyBranchMapper {

    @Override
    public MilitaryArmyBranchDTO toDto(MilitaryArmyBranchEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MilitaryArmyBranchDTO militaryArmyBranchDTO = new MilitaryArmyBranchDTO();

        militaryArmyBranchDTO.setId( entity.getId() );
        militaryArmyBranchDTO.setArmyBranchName( entity.getArmyBranchName() );

        return militaryArmyBranchDTO;
    }

    @Override
    public MilitaryArmyBranchEntity toEntity(MilitaryArmyBranchDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MilitaryArmyBranchEntity militaryArmyBranchEntity = new MilitaryArmyBranchEntity();

        militaryArmyBranchEntity.setId( dto.getId() );
        militaryArmyBranchEntity.setArmyBranchName( dto.getArmyBranchName() );

        return militaryArmyBranchEntity;
    }
}
