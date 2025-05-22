package cz.rodro.service;

import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.DistrictDTO;
import cz.rodro.dto.ProvinceDTO;
import cz.rodro.dto.mapper.DistrictMapper;
import cz.rodro.dto.mapper.ProvinceMapper;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.ProvinceEntity;
import cz.rodro.entity.repository.CountryRepository;
import cz.rodro.entity.repository.DistrictRepository;
import cz.rodro.entity.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import cz.rodro.exception.NotFoundException;

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
        // Find the country from the CountryDTO (assuming the country is already provided in the DTO)
        CountryEntity countryEntity = countryRepository.findById(provinceDTO.getCountry().getId())
                .orElseThrow(() -> new NotFoundException("Country not found"));

        // Map the ProvinceDTO to ProvinceEntity and associate the country
        ProvinceEntity provinceEntity = provinceMapper.toProvinceEntity(provinceDTO);
        provinceEntity.setCountry(countryEntity);

        // Save the entity and return the DTO
        provinceEntity = provinceRepository.save(provinceEntity);
        return provinceMapper.toProvinceDTO(provinceEntity);
    }

    @Override
    public ProvinceDTO getProvince(long provinceId) {
        ProvinceEntity provinceEntity = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new NotFoundException("Province not found"));

        ProvinceDTO provinceDTO = provinceMapper.toProvinceDTO(provinceEntity);

        // Manually populate districts
        List<DistrictDTO> districtDTOs = provinceEntity.getDistricts().stream()
                .map(districtMapper::toDistrictDTO)
                .collect(Collectors.toList());

        provinceDTO.setDistricts(districtDTOs);
        return provinceDTO;
    }

    @Override
    public void removeProvince(long provinceId) {
        ProvinceEntity provinceEntity = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new NotFoundException("Province not found"));

        provinceRepository.delete(provinceEntity);
    }

    @Override
    public ProvinceDTO updateProvince(Long provinceId, ProvinceDTO provinceDTO) {
        ProvinceEntity provinceEntity = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new NotFoundException("Province not found"));

        // Update the entity's fields using the provided DTO
        provinceMapper.updateProvinceEntity(provinceDTO, provinceEntity);

        // Optionally update the associated country if needed (like in the addProvince method)
        if (provinceDTO.getCountry() != null) {
            CountryEntity countryEntity = countryRepository.findById(provinceDTO.getCountry().getId())
                    .orElseThrow(() -> new NotFoundException("Country not found"));
            provinceEntity.setCountry(countryEntity);
        }

        provinceEntity = provinceRepository.save(provinceEntity);
        return provinceMapper.toProvinceDTO(provinceEntity);
    }

    @Override
    public List<ProvinceDTO> getProvincesByCountry(long countryId) {
        List<ProvinceEntity> provinces = provinceRepository.findByCountryId(countryId);

        return provinces.stream()
                .map(province -> {
                    ProvinceDTO provinceDTO = provinceMapper.toProvinceDTO(province);

                    // Manually populate districts
                    List<DistrictDTO> districtDTOs = province.getDistricts().stream()
                            .map(districtMapper::toDistrictDTO)
                            .collect(Collectors.toList());

                    provinceDTO.setDistricts(districtDTOs);
                    return provinceDTO;
                })
                .collect(Collectors.toList());
    }
}
