package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.entity.MilitaryArmyBranchEntity;
import cz.rodro.entity.MilitaryRankEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class MilitaryRankMapperImpl implements MilitaryRankMapper {

    @Override
    public MilitaryRankDTO toMilitaryRankDTO(MilitaryRankEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MilitaryRankDTO militaryRankDTO = new MilitaryRankDTO();

        militaryRankDTO.setArmyBranchName( entityArmyBranchArmyBranchName( entity ) );
        militaryRankDTO.setId( entity.getId() );
        militaryRankDTO.setRankName( entity.getRankName() );
        militaryRankDTO.setRankDescription( entity.getRankDescription() );
        militaryRankDTO.setRankLevel( entity.getRankLevel() );
        militaryRankDTO.setActiveFromYear( entity.getActiveFromYear() );
        militaryRankDTO.setActiveToYear( entity.getActiveToYear() );
        militaryRankDTO.setNotes( entity.getNotes() );

        return militaryRankDTO;
    }

    @Override
    public MilitaryRankEntity toMilitaryRankEntity(MilitaryRankDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MilitaryRankEntity militaryRankEntity = new MilitaryRankEntity();

        militaryRankEntity.setId( dto.getId() );
        militaryRankEntity.setRankName( dto.getRankName() );
        militaryRankEntity.setRankDescription( dto.getRankDescription() );
        militaryRankEntity.setRankLevel( dto.getRankLevel() );
        militaryRankEntity.setActiveFromYear( dto.getActiveFromYear() );
        militaryRankEntity.setActiveToYear( dto.getActiveToYear() );
        militaryRankEntity.setNotes( dto.getNotes() );

        return militaryRankEntity;
    }

    @Override
    public void updateMilitaryRankEntity(MilitaryRankDTO dto, MilitaryRankEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setRankName( dto.getRankName() );
        entity.setRankDescription( dto.getRankDescription() );
        entity.setRankLevel( dto.getRankLevel() );
        entity.setActiveFromYear( dto.getActiveFromYear() );
        entity.setActiveToYear( dto.getActiveToYear() );
        entity.setNotes( dto.getNotes() );
    }

    private String entityArmyBranchArmyBranchName(MilitaryRankEntity militaryRankEntity) {
        if ( militaryRankEntity == null ) {
            return null;
        }
        MilitaryArmyBranchEntity armyBranch = militaryRankEntity.getArmyBranch();
        if ( armyBranch == null ) {
            return null;
        }
        String armyBranchName = armyBranch.getArmyBranchName();
        if ( armyBranchName == null ) {
            return null;
        }
        return armyBranchName;
    }
}
