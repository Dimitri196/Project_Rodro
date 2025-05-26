package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.entity.MilitaryOrganizationEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class MilitaryOrganizationMapperImpl implements MilitaryOrganizationMapper {

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public MilitaryOrganizationDTO toMilitaryOrganizationDTO(MilitaryOrganizationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MilitaryOrganizationDTO militaryOrganizationDTO = new MilitaryOrganizationDTO();

        militaryOrganizationDTO.setId( entity.getId() );
        militaryOrganizationDTO.setArmyName( entity.getArmyName() );
        militaryOrganizationDTO.setArmyBranch( entity.getArmyBranch() );
        militaryOrganizationDTO.setCountry( countryMapper.toCountryDTO( entity.getCountry() ) );
        militaryOrganizationDTO.setActiveFromYear( entity.getActiveFromYear() );
        militaryOrganizationDTO.setActiveToYear( entity.getActiveToYear() );

        return militaryOrganizationDTO;
    }

    @Override
    public MilitaryOrganizationEntity toMilitaryOrganizationEntity(MilitaryOrganizationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MilitaryOrganizationEntity militaryOrganizationEntity = new MilitaryOrganizationEntity();

        militaryOrganizationEntity.setId( dto.getId() );
        militaryOrganizationEntity.setArmyName( dto.getArmyName() );
        militaryOrganizationEntity.setArmyBranch( dto.getArmyBranch() );
        militaryOrganizationEntity.setCountry( countryMapper.toCountryEntity( dto.getCountry() ) );
        militaryOrganizationEntity.setActiveFromYear( dto.getActiveFromYear() );
        militaryOrganizationEntity.setActiveToYear( dto.getActiveToYear() );

        return militaryOrganizationEntity;
    }

    @Override
    public void updateMilitaryOrganizationEntity(MilitaryOrganizationDTO dto, MilitaryOrganizationEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setArmyName( dto.getArmyName() );
        entity.setArmyBranch( dto.getArmyBranch() );
        entity.setCountry( countryMapper.toCountryEntity( dto.getCountry() ) );
        entity.setActiveFromYear( dto.getActiveFromYear() );
        entity.setActiveToYear( dto.getActiveToYear() );
    }
}
