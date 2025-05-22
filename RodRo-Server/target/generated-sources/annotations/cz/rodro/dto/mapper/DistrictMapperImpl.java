package cz.rodro.dto.mapper;

import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.DistrictDTO;
import cz.rodro.dto.ProvinceDTO;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.DistrictEntity;
import cz.rodro.entity.ProvinceEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class DistrictMapperImpl implements DistrictMapper {

    @Override
    public DistrictDTO toDistrictDTO(DistrictEntity districtEntity) {
        if ( districtEntity == null ) {
            return null;
        }

        DistrictDTO districtDTO = new DistrictDTO();

        districtDTO.setId( districtEntity.getId() );
        districtDTO.setDistrictName( districtEntity.getDistrictName() );

        return districtDTO;
    }

    @Override
    public DistrictEntity toDistrictEntity(DistrictDTO districtDTO) {
        if ( districtDTO == null ) {
            return null;
        }

        DistrictEntity districtEntity = new DistrictEntity();

        districtEntity.setId( districtDTO.getId() );
        districtEntity.setDistrictName( districtDTO.getDistrictName() );

        return districtEntity;
    }

    @Override
    public void updateDistrictEntity(DistrictDTO districtDTO, DistrictEntity districtEntity) {
        if ( districtDTO == null ) {
            return;
        }

        districtEntity.setId( districtDTO.getId() );
        districtEntity.setDistrictName( districtDTO.getDistrictName() );
        if ( districtDTO.getProvince() != null ) {
            if ( districtEntity.getProvince() == null ) {
                districtEntity.setProvince( new ProvinceEntity() );
            }
            provinceDTOToProvinceEntity( districtDTO.getProvince(), districtEntity.getProvince() );
        }
        else {
            districtEntity.setProvince( null );
        }
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

    protected List<DistrictEntity> districtDTOListToDistrictEntityList(List<DistrictDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<DistrictEntity> list1 = new ArrayList<DistrictEntity>( list.size() );
        for ( DistrictDTO districtDTO : list ) {
            list1.add( toDistrictEntity( districtDTO ) );
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

    protected void countryDTOToCountryEntity(CountryDTO countryDTO, CountryEntity mappingTarget) {
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

    protected void provinceDTOToProvinceEntity(ProvinceDTO provinceDTO, ProvinceEntity mappingTarget) {
        if ( provinceDTO == null ) {
            return;
        }

        mappingTarget.setId( provinceDTO.getId() );
        mappingTarget.setProvinceName( provinceDTO.getProvinceName() );
        if ( provinceDTO.getCountry() != null ) {
            if ( mappingTarget.getCountry() == null ) {
                mappingTarget.setCountry( new CountryEntity() );
            }
            countryDTOToCountryEntity( provinceDTO.getCountry(), mappingTarget.getCountry() );
        }
        else {
            mappingTarget.setCountry( null );
        }
        if ( mappingTarget.getDistricts() != null ) {
            List<DistrictEntity> list = districtDTOListToDistrictEntityList( provinceDTO.getDistricts() );
            if ( list != null ) {
                mappingTarget.getDistricts().clear();
                mappingTarget.getDistricts().addAll( list );
            }
            else {
                mappingTarget.setDistricts( null );
            }
        }
        else {
            List<DistrictEntity> list = districtDTOListToDistrictEntityList( provinceDTO.getDistricts() );
            if ( list != null ) {
                mappingTarget.setDistricts( list );
            }
        }
    }
}
