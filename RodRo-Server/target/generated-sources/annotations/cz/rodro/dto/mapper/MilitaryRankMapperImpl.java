package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.entity.MilitaryOrganizationEntity;
import cz.rodro.entity.MilitaryRankEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class MilitaryRankMapperImpl implements MilitaryRankMapper {

    @Autowired
    private MilitaryOrganizationMapper militaryOrganizationMapper;

    @Override
    public MilitaryRankDTO toMilitaryRankDTO(MilitaryRankEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MilitaryRankDTO militaryRankDTO = new MilitaryRankDTO();

        militaryRankDTO.setId( entity.getId() );
        militaryRankDTO.setRankName( entity.getRankName() );
        militaryRankDTO.setRankDescription( entity.getRankDescription() );
        militaryRankDTO.setRankLevel( entity.getRankLevel() );
        militaryRankDTO.setOrganization( militaryOrganizationMapper.toMilitaryOrganizationDTO( entity.getOrganization() ) );
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
        militaryRankEntity.setOrganization( militaryOrganizationMapper.toMilitaryOrganizationEntity( dto.getOrganization() ) );
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
        if ( dto.getOrganization() != null ) {
            if ( entity.getOrganization() == null ) {
                entity.setOrganization( new MilitaryOrganizationEntity() );
            }
            militaryOrganizationMapper.updateMilitaryOrganizationEntity( dto.getOrganization(), entity.getOrganization() );
        }
        else {
            entity.setOrganization( null );
        }
        entity.setActiveFromYear( dto.getActiveFromYear() );
        entity.setActiveToYear( dto.getActiveToYear() );
        entity.setNotes( dto.getNotes() );
    }
}
