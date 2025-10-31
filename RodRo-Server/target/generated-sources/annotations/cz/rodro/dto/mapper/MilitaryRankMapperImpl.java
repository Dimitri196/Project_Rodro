package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.dto.MilitaryStructureSimpleDTO;
import cz.rodro.entity.MilitaryOrganizationEntity;
import cz.rodro.entity.MilitaryRankEntity;
import cz.rodro.entity.MilitaryStructureEntity;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private MilitaryStructureSimpleMapper militaryStructureSimpleMapper;

    @Override
    public MilitaryRankDTO toMilitaryRankDTO(MilitaryRankEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MilitaryRankDTO militaryRankDTO = new MilitaryRankDTO();

        militaryRankDTO.setMilitaryOrganization( militaryOrganizationMapper.toMilitaryOrganizationDTO( entity.getMilitaryOrganization() ) );
        militaryRankDTO.setMilitaryStructureDTO( militaryStructureEntityToMilitaryStructureDTO( entity.getMilitaryStructure() ) );
        militaryRankDTO.setRankImageUrl( entity.getRankImageUrl() );
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

        militaryRankEntity.setMilitaryOrganization( militaryOrganizationMapper.toMilitaryOrganizationEntity( dto.getMilitaryOrganization() ) );
        militaryRankEntity.setMilitaryStructure( militaryStructureDTOToMilitaryStructureEntity( dto.getMilitaryStructureDTO() ) );
        militaryRankEntity.setRankImageUrl( dto.getRankImageUrl() );
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
        entity.setRankImageUrl( dto.getRankImageUrl() );
        if ( dto.getMilitaryOrganization() != null ) {
            if ( entity.getMilitaryOrganization() == null ) {
                entity.setMilitaryOrganization( new MilitaryOrganizationEntity() );
            }
            militaryOrganizationMapper.updateMilitaryOrganizationEntity( dto.getMilitaryOrganization(), entity.getMilitaryOrganization() );
        }
        else {
            entity.setMilitaryOrganization( null );
        }
    }

    protected List<MilitaryStructureSimpleDTO> militaryStructureEntityListToMilitaryStructureSimpleDTOList(List<MilitaryStructureEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<MilitaryStructureSimpleDTO> list1 = new ArrayList<MilitaryStructureSimpleDTO>( list.size() );
        for ( MilitaryStructureEntity militaryStructureEntity : list ) {
            list1.add( militaryStructureSimpleMapper.toMilitaryStructureSimpleDTO( militaryStructureEntity ) );
        }

        return list1;
    }

    protected MilitaryStructureDTO militaryStructureEntityToMilitaryStructureDTO(MilitaryStructureEntity militaryStructureEntity) {
        if ( militaryStructureEntity == null ) {
            return null;
        }

        MilitaryStructureDTO militaryStructureDTO = new MilitaryStructureDTO();

        militaryStructureDTO.setId( militaryStructureEntity.getId() );
        militaryStructureDTO.setUnitName( militaryStructureEntity.getUnitName() );
        militaryStructureDTO.setUnitType( militaryStructureEntity.getUnitType() );
        militaryStructureDTO.setActiveFromYear( militaryStructureEntity.getActiveFromYear() );
        militaryStructureDTO.setActiveToYear( militaryStructureEntity.getActiveToYear() );
        militaryStructureDTO.setNotes( militaryStructureEntity.getNotes() );
        militaryStructureDTO.setBannerImageUrl( militaryStructureEntity.getBannerImageUrl() );
        militaryStructureDTO.setParentStructure( militaryStructureSimpleMapper.toMilitaryStructureSimpleDTO( militaryStructureEntity.getParentStructure() ) );
        militaryStructureDTO.setSubStructures( militaryStructureEntityListToMilitaryStructureSimpleDTOList( militaryStructureEntity.getSubStructures() ) );
        militaryStructureDTO.setOrganization( militaryOrganizationMapper.toMilitaryOrganizationDTO( militaryStructureEntity.getOrganization() ) );

        return militaryStructureDTO;
    }

    protected MilitaryStructureEntity militaryStructureSimpleDTOToMilitaryStructureEntity(MilitaryStructureSimpleDTO militaryStructureSimpleDTO) {
        if ( militaryStructureSimpleDTO == null ) {
            return null;
        }

        MilitaryStructureEntity militaryStructureEntity = new MilitaryStructureEntity();

        militaryStructureEntity.setId( militaryStructureSimpleDTO.getId() );
        militaryStructureEntity.setUnitName( militaryStructureSimpleDTO.getUnitName() );

        return militaryStructureEntity;
    }

    protected List<MilitaryStructureEntity> militaryStructureSimpleDTOListToMilitaryStructureEntityList(List<MilitaryStructureSimpleDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<MilitaryStructureEntity> list1 = new ArrayList<MilitaryStructureEntity>( list.size() );
        for ( MilitaryStructureSimpleDTO militaryStructureSimpleDTO : list ) {
            list1.add( militaryStructureSimpleDTOToMilitaryStructureEntity( militaryStructureSimpleDTO ) );
        }

        return list1;
    }

    protected MilitaryStructureEntity militaryStructureDTOToMilitaryStructureEntity(MilitaryStructureDTO militaryStructureDTO) {
        if ( militaryStructureDTO == null ) {
            return null;
        }

        MilitaryStructureEntity militaryStructureEntity = new MilitaryStructureEntity();

        militaryStructureEntity.setId( militaryStructureDTO.getId() );
        militaryStructureEntity.setUnitName( militaryStructureDTO.getUnitName() );
        militaryStructureEntity.setUnitType( militaryStructureDTO.getUnitType() );
        militaryStructureEntity.setActiveFromYear( militaryStructureDTO.getActiveFromYear() );
        militaryStructureEntity.setActiveToYear( militaryStructureDTO.getActiveToYear() );
        militaryStructureEntity.setNotes( militaryStructureDTO.getNotes() );
        militaryStructureEntity.setBannerImageUrl( militaryStructureDTO.getBannerImageUrl() );
        militaryStructureEntity.setOrganization( militaryOrganizationMapper.toMilitaryOrganizationEntity( militaryStructureDTO.getOrganization() ) );
        militaryStructureEntity.setParentStructure( militaryStructureSimpleDTOToMilitaryStructureEntity( militaryStructureDTO.getParentStructure() ) );
        militaryStructureEntity.setSubStructures( militaryStructureSimpleDTOListToMilitaryStructureEntityList( militaryStructureDTO.getSubStructures() ) );

        return militaryStructureEntity;
    }
}
