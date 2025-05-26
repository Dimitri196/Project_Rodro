package cz.rodro.dto.mapper;

import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.MilitaryOrganizationEntity;
import cz.rodro.entity.MilitaryStructureEntity;
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
    private MilitaryOrganizationMapper militaryOrganizationMapper;

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
        militaryStructureEntity.setOrganization( militaryOrganizationMapper.toMilitaryOrganizationEntity( dto.getOrganization() ) );
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
            militaryOrganizationMapper.updateMilitaryOrganizationEntity( dto.getOrganization(), entity.getOrganization() );
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
}
