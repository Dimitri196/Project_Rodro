package cz.rodro.dto.mapper;

import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.DistrictDTO;
import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.dto.MilitaryStructureSimpleDTO;
import cz.rodro.dto.ProvinceDTO;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.DistrictEntity;
import cz.rodro.entity.MilitaryArmyBranchEntity;
import cz.rodro.entity.MilitaryOrganizationEntity;
import cz.rodro.entity.MilitaryStructureEntity;
import cz.rodro.entity.ProvinceEntity;
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
    private MilitaryOrganizationMapper militaryOrganizationMapper;
    @Autowired
    private MilitaryArmyBranchMapper militaryArmyBranchMapper;

    @Override
    public MilitaryStructureDTO toMilitaryStructureDTO(MilitaryStructureEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MilitaryStructureDTO militaryStructureDTO = new MilitaryStructureDTO();

        militaryStructureDTO.setBannerImageUrl( entity.getBannerImageUrl() );
        militaryStructureDTO.setArmyBranchName( entityOrganizationArmyBranchArmyBranchName( entity ) );
        militaryStructureDTO.setParentStructure( militaryStructureEntityToMilitaryStructureSimpleDTO( entity.getParentStructure() ) );
        militaryStructureDTO.setSubStructures( militaryStructureEntityListToMilitaryStructureSimpleDTOList( entity.getSubStructures() ) );
        militaryStructureDTO.setId( entity.getId() );
        militaryStructureDTO.setUnitName( entity.getUnitName() );
        militaryStructureDTO.setUnitType( entity.getUnitType() );
        militaryStructureDTO.setActiveFromYear( entity.getActiveFromYear() );
        militaryStructureDTO.setActiveToYear( entity.getActiveToYear() );
        militaryStructureDTO.setNotes( entity.getNotes() );
        militaryStructureDTO.setOrganization( militaryOrganizationEntityToMilitaryOrganizationDTO( entity.getOrganization() ) );

        return militaryStructureDTO;
    }

    @Override
    public MilitaryStructureEntity toMilitaryStructureEntity(MilitaryStructureDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MilitaryStructureEntity militaryStructureEntity = new MilitaryStructureEntity();

        militaryStructureEntity.setBannerImageUrl( dto.getBannerImageUrl() );
        militaryStructureEntity.setParentStructure( militaryStructureSimpleDTOToMilitaryStructureEntity( dto.getParentStructure() ) );
        militaryStructureEntity.setSubStructures( militaryStructureSimpleDTOListToMilitaryStructureEntityList( dto.getSubStructures() ) );
        militaryStructureEntity.setId( dto.getId() );
        militaryStructureEntity.setUnitName( dto.getUnitName() );
        militaryStructureEntity.setUnitType( dto.getUnitType() );
        militaryStructureEntity.setActiveFromYear( dto.getActiveFromYear() );
        militaryStructureEntity.setActiveToYear( dto.getActiveToYear() );
        militaryStructureEntity.setNotes( dto.getNotes() );
        militaryStructureEntity.setOrganization( militaryOrganizationDTOToMilitaryOrganizationEntity( dto.getOrganization() ) );

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
        entity.setActiveFromYear( dto.getActiveFromYear() );
        entity.setActiveToYear( dto.getActiveToYear() );
        entity.setNotes( dto.getNotes() );
        entity.setBannerImageUrl( dto.getBannerImageUrl() );
        if ( dto.getOrganization() != null ) {
            if ( entity.getOrganization() == null ) {
                entity.setOrganization( new MilitaryOrganizationEntity() );
            }
            militaryOrganizationMapper.updateMilitaryOrganizationEntity( dto.getOrganization(), entity.getOrganization() );
        }
        else {
            entity.setOrganization( null );
        }
        if ( dto.getParentStructure() != null ) {
            if ( entity.getParentStructure() == null ) {
                entity.setParentStructure( new MilitaryStructureEntity() );
            }
            militaryStructureSimpleDTOToMilitaryStructureEntity1( dto.getParentStructure(), entity.getParentStructure() );
        }
        else {
            entity.setParentStructure( null );
        }
        if ( entity.getSubStructures() != null ) {
            List<MilitaryStructureEntity> list = militaryStructureSimpleDTOListToMilitaryStructureEntityList1( dto.getSubStructures() );
            if ( list != null ) {
                entity.getSubStructures().clear();
                entity.getSubStructures().addAll( list );
            }
            else {
                entity.setSubStructures( null );
            }
        }
        else {
            List<MilitaryStructureEntity> list = militaryStructureSimpleDTOListToMilitaryStructureEntityList1( dto.getSubStructures() );
            if ( list != null ) {
                entity.setSubStructures( list );
            }
        }
    }

    private String entityOrganizationArmyBranchArmyBranchName(MilitaryStructureEntity militaryStructureEntity) {
        if ( militaryStructureEntity == null ) {
            return null;
        }
        MilitaryOrganizationEntity organization = militaryStructureEntity.getOrganization();
        if ( organization == null ) {
            return null;
        }
        MilitaryArmyBranchEntity armyBranch = organization.getArmyBranch();
        if ( armyBranch == null ) {
            return null;
        }
        String armyBranchName = armyBranch.getArmyBranchName();
        if ( armyBranchName == null ) {
            return null;
        }
        return armyBranchName;
    }

    protected MilitaryStructureSimpleDTO militaryStructureEntityToMilitaryStructureSimpleDTO(MilitaryStructureEntity militaryStructureEntity) {
        if ( militaryStructureEntity == null ) {
            return null;
        }

        MilitaryStructureSimpleDTO militaryStructureSimpleDTO = new MilitaryStructureSimpleDTO();

        militaryStructureSimpleDTO.setId( militaryStructureEntity.getId() );
        militaryStructureSimpleDTO.setUnitName( militaryStructureEntity.getUnitName() );

        return militaryStructureSimpleDTO;
    }

    protected List<MilitaryStructureSimpleDTO> militaryStructureEntityListToMilitaryStructureSimpleDTOList(List<MilitaryStructureEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<MilitaryStructureSimpleDTO> list1 = new ArrayList<MilitaryStructureSimpleDTO>( list.size() );
        for ( MilitaryStructureEntity militaryStructureEntity : list ) {
            list1.add( militaryStructureEntityToMilitaryStructureSimpleDTO( militaryStructureEntity ) );
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

    protected MilitaryOrganizationDTO militaryOrganizationEntityToMilitaryOrganizationDTO(MilitaryOrganizationEntity militaryOrganizationEntity) {
        if ( militaryOrganizationEntity == null ) {
            return null;
        }

        MilitaryOrganizationDTO militaryOrganizationDTO = new MilitaryOrganizationDTO();

        militaryOrganizationDTO.setId( militaryOrganizationEntity.getId() );
        militaryOrganizationDTO.setArmyName( militaryOrganizationEntity.getArmyName() );
        militaryOrganizationDTO.setArmyBranch( militaryArmyBranchMapper.toDto( militaryOrganizationEntity.getArmyBranch() ) );
        militaryOrganizationDTO.setCountry( countryEntityToCountryDTO( militaryOrganizationEntity.getCountry() ) );
        militaryOrganizationDTO.setActiveFromYear( militaryOrganizationEntity.getActiveFromYear() );
        militaryOrganizationDTO.setActiveToYear( militaryOrganizationEntity.getActiveToYear() );
        militaryOrganizationDTO.setOrganizationImageUrl( militaryOrganizationEntity.getOrganizationImageUrl() );
        militaryOrganizationDTO.setOrganizationDescription( militaryOrganizationEntity.getOrganizationDescription() );

        return militaryOrganizationDTO;
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

    protected DistrictEntity districtDTOToDistrictEntity(DistrictDTO districtDTO) {
        if ( districtDTO == null ) {
            return null;
        }

        DistrictEntity districtEntity = new DistrictEntity();

        districtEntity.setId( districtDTO.getId() );
        districtEntity.setDistrictName( districtDTO.getDistrictName() );

        return districtEntity;
    }

    protected List<DistrictEntity> districtDTOListToDistrictEntityList(List<DistrictDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<DistrictEntity> list1 = new ArrayList<DistrictEntity>( list.size() );
        for ( DistrictDTO districtDTO : list ) {
            list1.add( districtDTOToDistrictEntity( districtDTO ) );
        }

        return list1;
    }

    protected ProvinceEntity provinceDTOToProvinceEntity(ProvinceDTO provinceDTO) {
        if ( provinceDTO == null ) {
            return null;
        }

        ProvinceEntity provinceEntity = new ProvinceEntity();

        provinceEntity.setId( provinceDTO.getId() );
        provinceEntity.setProvinceName( provinceDTO.getProvinceName() );
        provinceEntity.setCountry( countryDTOToCountryEntity( provinceDTO.getCountry() ) );
        provinceEntity.setDistricts( districtDTOListToDistrictEntityList( provinceDTO.getDistricts() ) );
        provinceEntity.setProvinceFlagImgUrl( provinceDTO.getProvinceFlagImgUrl() );

        return provinceEntity;
    }

    protected List<ProvinceEntity> provinceDTOListToProvinceEntityList(List<ProvinceDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<ProvinceEntity> list1 = new ArrayList<ProvinceEntity>( list.size() );
        for ( ProvinceDTO provinceDTO : list ) {
            list1.add( provinceDTOToProvinceEntity( provinceDTO ) );
        }

        return list1;
    }

    protected CountryEntity countryDTOToCountryEntity(CountryDTO countryDTO) {
        if ( countryDTO == null ) {
            return null;
        }

        CountryEntity countryEntity = new CountryEntity();

        countryEntity.setId( countryDTO.getId() );
        countryEntity.setCountryNameInPolish( countryDTO.getCountryNameInPolish() );
        countryEntity.setCountryNameInEnglish( countryDTO.getCountryNameInEnglish() );
        countryEntity.setCountryEstablishmentYear( countryDTO.getCountryEstablishmentYear() );
        countryEntity.setCountryCancellationYear( countryDTO.getCountryCancellationYear() );
        countryEntity.setProvinces( provinceDTOListToProvinceEntityList( countryDTO.getProvinces() ) );
        countryEntity.setCountryFlagImgUrl( countryDTO.getCountryFlagImgUrl() );

        return countryEntity;
    }

    protected MilitaryOrganizationEntity militaryOrganizationDTOToMilitaryOrganizationEntity(MilitaryOrganizationDTO militaryOrganizationDTO) {
        if ( militaryOrganizationDTO == null ) {
            return null;
        }

        MilitaryOrganizationEntity militaryOrganizationEntity = new MilitaryOrganizationEntity();

        militaryOrganizationEntity.setId( militaryOrganizationDTO.getId() );
        militaryOrganizationEntity.setArmyName( militaryOrganizationDTO.getArmyName() );
        militaryOrganizationEntity.setArmyBranch( militaryArmyBranchMapper.toEntity( militaryOrganizationDTO.getArmyBranch() ) );
        militaryOrganizationEntity.setCountry( countryDTOToCountryEntity( militaryOrganizationDTO.getCountry() ) );
        militaryOrganizationEntity.setActiveFromYear( militaryOrganizationDTO.getActiveFromYear() );
        militaryOrganizationEntity.setActiveToYear( militaryOrganizationDTO.getActiveToYear() );
        militaryOrganizationEntity.setOrganizationImageUrl( militaryOrganizationDTO.getOrganizationImageUrl() );
        militaryOrganizationEntity.setOrganizationDescription( militaryOrganizationDTO.getOrganizationDescription() );

        return militaryOrganizationEntity;
    }

    protected void militaryStructureSimpleDTOToMilitaryStructureEntity1(MilitaryStructureSimpleDTO militaryStructureSimpleDTO, MilitaryStructureEntity mappingTarget) {
        if ( militaryStructureSimpleDTO == null ) {
            return;
        }

        mappingTarget.setId( militaryStructureSimpleDTO.getId() );
        mappingTarget.setUnitName( militaryStructureSimpleDTO.getUnitName() );
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
