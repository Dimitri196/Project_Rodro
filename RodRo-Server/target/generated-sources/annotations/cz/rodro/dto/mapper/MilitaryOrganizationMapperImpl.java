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
public class MilitaryOrganizationMapperImpl implements MilitaryOrganizationMapper {

    @Autowired
    private CountryMapper countryMapper;
    @Autowired
    private MilitaryArmyBranchMapper militaryArmyBranchMapper;
    @Autowired
    private MilitaryStructureMapper militaryStructureMapper;

    @Override
    public MilitaryOrganizationDTO toMilitaryOrganizationDTO(MilitaryOrganizationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MilitaryOrganizationDTO militaryOrganizationDTO = new MilitaryOrganizationDTO();

        militaryOrganizationDTO.setId( entity.getId() );
        militaryOrganizationDTO.setArmyName( entity.getArmyName() );
        militaryOrganizationDTO.setArmyBranch( militaryArmyBranchMapper.toDto( entity.getArmyBranch() ) );
        militaryOrganizationDTO.setCountry( countryEntityToCountryDTO( entity.getCountry() ) );
        militaryOrganizationDTO.setActiveFromYear( entity.getActiveFromYear() );
        militaryOrganizationDTO.setActiveToYear( entity.getActiveToYear() );
        militaryOrganizationDTO.setStructures( militaryStructureEntityListToMilitaryStructureDTOList( entity.getStructures() ) );

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
        militaryOrganizationEntity.setArmyBranch( militaryArmyBranchMapper.toEntity( dto.getArmyBranch() ) );
        militaryOrganizationEntity.setCountry( countryMapper.toCountryEntity( dto.getCountry() ) );
        militaryOrganizationEntity.setActiveFromYear( dto.getActiveFromYear() );
        militaryOrganizationEntity.setActiveToYear( dto.getActiveToYear() );
        militaryOrganizationEntity.setStructures( militaryStructureDTOListToMilitaryStructureEntityList( dto.getStructures() ) );

        return militaryOrganizationEntity;
    }

    @Override
    public void updateMilitaryOrganizationEntity(MilitaryOrganizationDTO dto, MilitaryOrganizationEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setArmyName( dto.getArmyName() );
        entity.setArmyBranch( militaryArmyBranchMapper.toEntity( dto.getArmyBranch() ) );
        entity.setCountry( countryMapper.toCountryEntity( dto.getCountry() ) );
        entity.setActiveFromYear( dto.getActiveFromYear() );
        entity.setActiveToYear( dto.getActiveToYear() );
        if ( entity.getStructures() != null ) {
            List<MilitaryStructureEntity> list = militaryStructureDTOListToMilitaryStructureEntityList( dto.getStructures() );
            if ( list != null ) {
                entity.getStructures().clear();
                entity.getStructures().addAll( list );
            }
            else {
                entity.setStructures( null );
            }
        }
        else {
            List<MilitaryStructureEntity> list = militaryStructureDTOListToMilitaryStructureEntityList( dto.getStructures() );
            if ( list != null ) {
                entity.setStructures( list );
            }
        }
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

    protected List<MilitaryStructureDTO> militaryStructureEntityListToMilitaryStructureDTOList(List<MilitaryStructureEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<MilitaryStructureDTO> list1 = new ArrayList<MilitaryStructureDTO>( list.size() );
        for ( MilitaryStructureEntity militaryStructureEntity : list ) {
            list1.add( militaryStructureMapper.toMilitaryStructureDTO( militaryStructureEntity ) );
        }

        return list1;
    }

    protected List<MilitaryStructureEntity> militaryStructureDTOListToMilitaryStructureEntityList(List<MilitaryStructureDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<MilitaryStructureEntity> list1 = new ArrayList<MilitaryStructureEntity>( list.size() );
        for ( MilitaryStructureDTO militaryStructureDTO : list ) {
            list1.add( militaryStructureMapper.toMilitaryStructureEntity( militaryStructureDTO ) );
        }

        return list1;
    }
}
