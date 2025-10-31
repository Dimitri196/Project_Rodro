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
public class ProvinceMapperImpl implements ProvinceMapper {

    @Override
    public ProvinceDTO toProvinceDTO(ProvinceEntity provinceEntity) {
        if ( provinceEntity == null ) {
            return null;
        }

        ProvinceDTO provinceDTO = new ProvinceDTO();

        provinceDTO.setId( provinceEntity.getId() );
        provinceDTO.setProvinceName( provinceEntity.getProvinceName() );
        provinceDTO.setProvinceFlagImgUrl( provinceEntity.getProvinceFlagImgUrl() );

        return provinceDTO;
    }

    @Override
    public ProvinceEntity toProvinceEntity(ProvinceDTO provinceDTO) {
        if ( provinceDTO == null ) {
            return null;
        }

        ProvinceEntity provinceEntity = new ProvinceEntity();

        provinceEntity.setId( provinceDTO.getId() );
        provinceEntity.setProvinceName( provinceDTO.getProvinceName() );
        provinceEntity.setProvinceFlagImgUrl( provinceDTO.getProvinceFlagImgUrl() );

        return provinceEntity;
    }

    @Override
    public void updateProvinceEntity(ProvinceDTO dto, ProvinceEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setProvinceName( dto.getProvinceName() );
        if ( dto.getCountry() != null ) {
            if ( entity.getCountry() == null ) {
                entity.setCountry( new CountryEntity() );
            }
            countryDTOToCountryEntity( dto.getCountry(), entity.getCountry() );
        }
        else {
            entity.setCountry( null );
        }
        if ( entity.getDistricts() != null ) {
            List<DistrictEntity> list = districtDTOListToDistrictEntityList( dto.getDistricts() );
            if ( list != null ) {
                entity.getDistricts().clear();
                entity.getDistricts().addAll( list );
            }
            else {
                entity.setDistricts( null );
            }
        }
        else {
            List<DistrictEntity> list = districtDTOListToDistrictEntityList( dto.getDistricts() );
            if ( list != null ) {
                entity.setDistricts( list );
            }
        }
        entity.setProvinceFlagImgUrl( dto.getProvinceFlagImgUrl() );
    }

    protected List<ProvinceEntity> provinceDTOListToProvinceEntityList(List<ProvinceDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<ProvinceEntity> list1 = new ArrayList<ProvinceEntity>( list.size() );
        for ( ProvinceDTO provinceDTO : list ) {
            list1.add( toProvinceEntity( provinceDTO ) );
        }

        return list1;
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
        mappingTarget.setCountryFlagImgUrl( countryDTO.getCountryFlagImgUrl() );
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
}
