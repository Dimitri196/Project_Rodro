package cz.rodro.dto.mapper;

import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.MilitaryOrganizationEntity;
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
public class MilitaryStructureMapperImpl implements MilitaryStructureMapper {

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public MilitaryStructureDTO toMilitaryStructureDTO(MilitaryStructureEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MilitaryStructureDTO militaryStructureDTO = new MilitaryStructureDTO();

        militaryStructureDTO.setId( entity.getId() );
        militaryStructureDTO.setUnitName( entity.getUnitName() );
        militaryStructureDTO.setUnitType( entity.getUnitType() );
        militaryStructureDTO.setOrganization( militaryOrganizationEntityToMilitaryOrganizationDTO( entity.getOrganization() ) );
        militaryStructureDTO.setActiveFromYear( entity.getActiveFromYear() );
        militaryStructureDTO.setActiveToYear( entity.getActiveToYear() );
        militaryStructureDTO.setNotes( entity.getNotes() );

        return militaryStructureDTO;
    }

    @Override
    public MilitaryStructureEntity toMilitaryStructureEntity(MilitaryStructureDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MilitaryStructureEntity militaryStructureEntity = new MilitaryStructureEntity();

        militaryStructureEntity.setId( dto.getId() );
        militaryStructureEntity.setUnitName( dto.getUnitName() );
        militaryStructureEntity.setUnitType( dto.getUnitType() );
        militaryStructureEntity.setOrganization( militaryOrganizationDTOToMilitaryOrganizationEntity( dto.getOrganization() ) );
        militaryStructureEntity.setActiveFromYear( dto.getActiveFromYear() );
        militaryStructureEntity.setActiveToYear( dto.getActiveToYear() );
        militaryStructureEntity.setNotes( dto.getNotes() );

        return militaryStructureEntity;
    }

    @Override
    public void updateMilitaryStructureEntity(MilitaryStructureDTO dto, MilitaryStructureEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setUnitName( dto.getUnitName() );
        entity.setUnitType( dto.getUnitType() );
        if ( dto.getOrganization() != null ) {
            if ( entity.getOrganization() == null ) {
                entity.setOrganization( new MilitaryOrganizationEntity() );
            }
            militaryOrganizationDTOToMilitaryOrganizationEntity1( dto.getOrganization(), entity.getOrganization() );
        }
        else {
            entity.setOrganization( null );
        }
        entity.setActiveFromYear( dto.getActiveFromYear() );
        entity.setActiveToYear( dto.getActiveToYear() );
        entity.setNotes( dto.getNotes() );
    }

    protected CountryDTO countryEntityToCountryDTO(CountryEntity countryEntity) {
        if ( countryEntity == null ) {
            return null;
        }

        CountryDTO countryDTO = new CountryDTO();

        countryDTO.setId( countryEntity.getId() );
        countryDTO.setCountryNameInPolish( countryEntity.getCountryNameInPolish() );
        countryDTO.setCountryNameInEnglish( countryEntity.getCountryNameInEnglish() );
        countryDTO.setCountryEstablishmentYear( countryEntity.getCountryEstablishmentYear() );
        countryDTO.setCountryCancellationYear( countryEntity.getCountryCancellationYear() );

        return countryDTO;
    }

    protected MilitaryOrganizationDTO militaryOrganizationEntityToMilitaryOrganizationDTO(MilitaryOrganizationEntity militaryOrganizationEntity) {
        if ( militaryOrganizationEntity == null ) {
            return null;
        }

        MilitaryOrganizationDTO militaryOrganizationDTO = new MilitaryOrganizationDTO();

        militaryOrganizationDTO.setId( militaryOrganizationEntity.getId() );
        militaryOrganizationDTO.setArmyName( militaryOrganizationEntity.getArmyName() );
        militaryOrganizationDTO.setArmyBranch( militaryOrganizationEntity.getArmyBranch() );
        militaryOrganizationDTO.setCountry( countryEntityToCountryDTO( militaryOrganizationEntity.getCountry() ) );
        militaryOrganizationDTO.setActiveFromYear( militaryOrganizationEntity.getActiveFromYear() );
        militaryOrganizationDTO.setActiveToYear( militaryOrganizationEntity.getActiveToYear() );

        return militaryOrganizationDTO;
    }

    protected List<MilitaryStructureEntity> militaryStructureDTOListToMilitaryStructureEntityList(List<MilitaryStructureDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<MilitaryStructureEntity> list1 = new ArrayList<MilitaryStructureEntity>( list.size() );
        for ( MilitaryStructureDTO militaryStructureDTO : list ) {
            list1.add( toMilitaryStructureEntity( militaryStructureDTO ) );
        }

        return list1;
    }

    protected MilitaryOrganizationEntity militaryOrganizationDTOToMilitaryOrganizationEntity(MilitaryOrganizationDTO militaryOrganizationDTO) {
        if ( militaryOrganizationDTO == null ) {
            return null;
        }

        MilitaryOrganizationEntity militaryOrganizationEntity = new MilitaryOrganizationEntity();

        militaryOrganizationEntity.setId( militaryOrganizationDTO.getId() );
        militaryOrganizationEntity.setArmyName( militaryOrganizationDTO.getArmyName() );
        militaryOrganizationEntity.setArmyBranch( militaryOrganizationDTO.getArmyBranch() );
        militaryOrganizationEntity.setCountry( countryMapper.toCountryEntity( militaryOrganizationDTO.getCountry() ) );
        militaryOrganizationEntity.setActiveFromYear( militaryOrganizationDTO.getActiveFromYear() );
        militaryOrganizationEntity.setActiveToYear( militaryOrganizationDTO.getActiveToYear() );
        militaryOrganizationEntity.setStructures( militaryStructureDTOListToMilitaryStructureEntityList( militaryOrganizationDTO.getStructures() ) );

        return militaryOrganizationEntity;
    }

    protected void militaryOrganizationDTOToMilitaryOrganizationEntity1(MilitaryOrganizationDTO militaryOrganizationDTO, MilitaryOrganizationEntity mappingTarget) {
        if ( militaryOrganizationDTO == null ) {
            return;
        }

        mappingTarget.setId( militaryOrganizationDTO.getId() );
        mappingTarget.setArmyName( militaryOrganizationDTO.getArmyName() );
        mappingTarget.setArmyBranch( militaryOrganizationDTO.getArmyBranch() );
        mappingTarget.setCountry( countryMapper.toCountryEntity( militaryOrganizationDTO.getCountry() ) );
        mappingTarget.setActiveFromYear( militaryOrganizationDTO.getActiveFromYear() );
        mappingTarget.setActiveToYear( militaryOrganizationDTO.getActiveToYear() );
        if ( mappingTarget.getStructures() != null ) {
            List<MilitaryStructureEntity> list = militaryStructureDTOListToMilitaryStructureEntityList( militaryOrganizationDTO.getStructures() );
            if ( list != null ) {
                mappingTarget.getStructures().clear();
                mappingTarget.getStructures().addAll( list );
            }
            else {
                mappingTarget.setStructures( null );
            }
        }
        else {
            List<MilitaryStructureEntity> list = militaryStructureDTOListToMilitaryStructureEntityList( militaryOrganizationDTO.getStructures() );
            if ( list != null ) {
                mappingTarget.setStructures( list );
            }
        }
    }
}
