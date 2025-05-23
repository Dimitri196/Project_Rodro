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
public class CountryMapperImpl implements CountryMapper {

    @Override
    public CountryDTO toCountryDTO(CountryEntity countryEntity) {
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

    @Override
    public CountryEntity toCountryEntity(CountryDTO countryDTO) {
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
        provinceEntity.setCountry( toCountryEntity( provinceDTO.getCountry() ) );
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
}
