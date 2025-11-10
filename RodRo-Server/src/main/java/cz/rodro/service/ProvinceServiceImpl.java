package cz.rodro.service;

import cz.rodro.dto.DistrictDTO;
import cz.rodro.dto.ProvinceDTO;
import cz.rodro.dto.mapper.DistrictMapper;
import cz.rodro.dto.mapper.ProvinceMapper;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.ProvinceEntity;
import cz.rodro.entity.repository.CountryRepository;
import cz.rodro.entity.repository.ProvinceRepository;
import cz.rodro.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private DistrictMapper districtMapper;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public ProvinceDTO addProvince(ProvinceDTO provinceDTO) {
        CountryEntity countryEntity = countryRepository.findById(provinceDTO.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ID: " + provinceDTO.getCountryId()));

        ProvinceEntity provinceEntity = provinceMapper.toProvinceEntity(provinceDTO);
        provinceEntity.setCountry(countryEntity); // Associate the fetched CountryEntity

        provinceEntity = provinceRepository.save(provinceEntity);

        return provinceMapper.toProvinceDTO(provinceEntity);
    }

    @Override
    public ProvinceDTO getProvince(long provinceId) {
        ProvinceEntity provinceEntity = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found"));

        ProvinceDTO provinceDTO = provinceMapper.toProvinceDTO(provinceEntity);

        List<DistrictDTO> districtDTOs = provinceEntity.getDistricts().stream()
                .map(districtMapper::toDistrictDTO)
                .collect(Collectors.toList());

        provinceDTO.setDistricts(districtDTOs);
        return provinceDTO;
    }

    @Override
    public void removeProvince(long provinceId) {
        ProvinceEntity provinceEntity = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found"));

        provinceRepository.delete(provinceEntity);
    }

    @Override
    public ProvinceDTO updateProvince(Long provinceId, ProvinceDTO provinceDTO) {
        ProvinceEntity provinceEntity = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found"));

        provinceMapper.updateProvinceEntity(provinceDTO, provinceEntity);

        if (provinceDTO.getCountryId() != null) {
            CountryEntity countryEntity = countryRepository.findById(provinceDTO.getCountryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Country not found with ID: " + provinceDTO.getCountryId()));
            provinceEntity.setCountry(countryEntity);
        } else if (provinceEntity.getCountry() != null) {

        }

        provinceEntity = provinceRepository.save(provinceEntity);

        return provinceMapper.toProvinceDTO(provinceEntity);
    }

    @Override
    public List<ProvinceDTO> getProvincesByCountry(long countryId) {

        List<ProvinceEntity> provinces = provinceRepository.findByCountry_Id(countryId);

        return provinces.stream()
                .map(province -> {

                    ProvinceDTO provinceDTO = provinceMapper.toProvinceDTO(province);

                    List<DistrictDTO> districtDTOs = province.getDistricts().stream()
                            .map(districtMapper::toDistrictDTO)
                            .collect(Collectors.toList());

                    provinceDTO.setDistricts(districtDTOs);
                    return provinceDTO;
                })
                .collect(Collectors.toList());
    }
}
