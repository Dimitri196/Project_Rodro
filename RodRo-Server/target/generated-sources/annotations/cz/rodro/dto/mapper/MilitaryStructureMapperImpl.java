package cz.rodro.dto.mapper;

import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.DistrictDTO;
import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.dto.MilitaryStructureDTO;
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
    private MilitaryArmyBranchMapper militaryArmyBranchMapper;

    @Override
    public MilitaryStructureDTO toMilitaryStructureDTO(MilitaryStructureEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MilitaryStructureDTO militaryStructureDTO = new MilitaryStructureDTO();

        militaryStructureDTO.setArmyBranchName( entityArmyBranchArmyBranchName( entity ) );
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

    private String entityArmyBranchArmyBranchName(MilitaryStructureEntity militaryStructureEntity) {
        if ( militaryStructureEntity == null ) {
            return null;
        }
        MilitaryArmyBranchEntity armyBranch = militaryStructureEntity.getArmyBranch();
        if ( armyBranch == null ) {
            return null;
        }
        String armyBranchName = armyBranch.getArmyBranchName();
        if ( armyBranchName == null ) {
            return null;
        }
        return armyBranchName;
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
        militaryOrganizationDTO.setArmyBranch( militaryArmyBranchMapper.toDto( militaryOrganizationEntity.getArmyBranch() ) );
        militaryOrganizationDTO.setCountry( countryEntityToCountryDTO( militaryOrganizationEntity.getCountry() ) );
        militaryOrganizationDTO.setActiveFromYear( militaryOrganizationEntity.getActiveFromYear() );
        militaryOrganizationDTO.setActiveToYear( militaryOrganizationEntity.getActiveToYear() );

        return militaryOrganizationDTO;
    }

    protected DistrictEntity districtDTOToDistrictEntity(DistrictDTO districtDTO) {
        if ( districtDTO == null ) {
            return null;
        }

        DistrictEntity districtEntity = new DistrictEntity();

        districtEntity.setId( districtDTO.getId() );
        districtEntity.setDistrictName( districtDTO.getDistrictName() );
        districtEntity.setProvince( provinceDTOToProvinceEntity( districtDTO.getProvince() ) );

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

        return militaryOrganizationEntity;
    }

    protected void countryDTOToCountryEntity1(CountryDTO countryDTO, CountryEntity mappingTarget) {
        if ( countryDTO == null ) {
            return;
        }

        mappingTarget.setId( countryDTO.getId() );
        mappingTarget.setCountryNameInPolish( countryDTO.getCountryNameInPolish() );
        mappingTarget.setCountryNameInEnglish( countryDTO.getCountryNameInEnglish() );
        mappingTarget.setCountryEstablishmentYear( countryDTO.getCountryEstablishmentYear() );
        mappingTarget.setCountryCancellationYear( countryDTO.getCountryCancellationYear() );
        if ( mappingTarget.getProvinces() != null ) {
            List<ProvinceEntity> list = provinceDTOListToProvinceEntityList( countryDTO.getProvinces() );
            if ( list != null ) {
                mappingTarget.getProvinces().clear();
                mappingTarget.getProvinces().addAll( list );
            }
            else {
                mappingTarget.setProvinces( null );
            }
        }
        else {
            List<ProvinceEntity> list = provinceDTOListToProvinceEntityList( countryDTO.getProvinces() );
            if ( list != null ) {
                mappingTarget.setProvinces( list );
            }
        }
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

    protected void militaryOrganizationDTOToMilitaryOrganizationEntity1(MilitaryOrganizationDTO militaryOrganizationDTO, MilitaryOrganizationEntity mappingTarget) {
        if ( militaryOrganizationDTO == null ) {
            return;
        }

        mappingTarget.setId( militaryOrganizationDTO.getId() );
        mappingTarget.setArmyName( militaryOrganizationDTO.getArmyName() );
        mappingTarget.setArmyBranch( militaryArmyBranchMapper.toEntity( militaryOrganizationDTO.getArmyBranch() ) );
        if ( militaryOrganizationDTO.getCountry() != null ) {
            if ( mappingTarget.getCountry() == null ) {
                mappingTarget.setCountry( new CountryEntity() );
            }
            countryDTOToCountryEntity1( militaryOrganizationDTO.getCountry(), mappingTarget.getCountry() );
        }
        else {
            mappingTarget.setCountry( null );
        }
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
