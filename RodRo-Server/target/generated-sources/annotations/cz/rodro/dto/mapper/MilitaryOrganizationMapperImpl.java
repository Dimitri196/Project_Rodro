package cz.rodro.dto.mapper;

import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.dto.MilitaryStructureSimpleDTO;
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
    private MilitaryArmyBranchMapper militaryArmyBranchMapper;
    @Autowired
    private CountryMapper countryMapper;
    @Autowired
    private MilitaryStructureSimpleMapper militaryStructureSimpleMapper;

    @Override
    public MilitaryOrganizationDTO toMilitaryOrganizationDTO(MilitaryOrganizationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MilitaryOrganizationDTO militaryOrganizationDTO = new MilitaryOrganizationDTO();

        militaryOrganizationDTO.setStructures( militaryStructureEntityListToMilitaryStructureSimpleDTOList( entity.getStructures() ) );
        militaryOrganizationDTO.setOrganizationImageUrl( entity.getOrganizationImageUrl() );
        militaryOrganizationDTO.setOrganizationDescription( entity.getOrganizationDescription() );
        militaryOrganizationDTO.setId( entity.getId() );
        militaryOrganizationDTO.setArmyName( entity.getArmyName() );
        militaryOrganizationDTO.setArmyBranch( militaryArmyBranchMapper.toDto( entity.getArmyBranch() ) );
        militaryOrganizationDTO.setCountry( countryEntityToCountryDTO( entity.getCountry() ) );
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

        militaryOrganizationEntity.setStructures( militaryStructureSimpleDTOListToMilitaryStructureEntityList( dto.getStructures() ) );
        militaryOrganizationEntity.setOrganizationDescription( dto.getOrganizationDescription() );
        militaryOrganizationEntity.setId( dto.getId() );
        militaryOrganizationEntity.setArmyName( dto.getArmyName() );
        militaryOrganizationEntity.setArmyBranch( militaryArmyBranchMapper.toEntity( dto.getArmyBranch() ) );
        militaryOrganizationEntity.setActiveFromYear( dto.getActiveFromYear() );
        militaryOrganizationEntity.setActiveToYear( dto.getActiveToYear() );
        militaryOrganizationEntity.setOrganizationImageUrl( dto.getOrganizationImageUrl() );

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
            List<MilitaryStructureEntity> list = militaryStructureSimpleDTOListToMilitaryStructureEntityList1( dto.getStructures() );
            if ( list != null ) {
                entity.getStructures().clear();
                entity.getStructures().addAll( list );
            }
            else {
                entity.setStructures( null );
            }
        }
        else {
            List<MilitaryStructureEntity> list = militaryStructureSimpleDTOListToMilitaryStructureEntityList1( dto.getStructures() );
            if ( list != null ) {
                entity.setStructures( list );
            }
        }
        entity.setOrganizationImageUrl( dto.getOrganizationImageUrl() );
        entity.setOrganizationDescription( dto.getOrganizationDescription() );
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
        countryDTO.setCountryFlagImgUrl( countryEntity.getCountryFlagImgUrl() );

        return countryDTO;
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

    protected List<MilitaryStructureEntity> militaryStructureSimpleDTOListToMilitaryStructureEntityList1(List<MilitaryStructureSimpleDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<MilitaryStructureEntity> list1 = new ArrayList<MilitaryStructureEntity>( list.size() );
        for ( MilitaryStructureSimpleDTO militaryStructureSimpleDTO : list ) {
            list1.add( militaryStructureSimpleDTOToMilitaryStructureEntity( militaryStructureSimpleDTO ) );
        }

        return list1;
    }
}
