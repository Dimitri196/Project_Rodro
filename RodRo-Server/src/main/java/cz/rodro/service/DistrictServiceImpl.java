package cz.rodro.service;

import cz.rodro.dto.DistrictDTO;
import cz.rodro.dto.mapper.DistrictMapper;
import cz.rodro.entity.DistrictEntity;
import cz.rodro.entity.ProvinceEntity;
import cz.rodro.entity.repository.CountryRepository;
import cz.rodro.entity.repository.DistrictRepository;
import cz.rodro.entity.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cz.rodro.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private DistrictMapper districtMapper;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public DistrictDTO addDistrict(DistrictDTO districtDTO) {
        // Find the province (country can be inferred from province)
        ProvinceEntity provinceEntity = provinceRepository.findById(districtDTO.getProvince().getId())
                .orElseThrow(() -> new NotFoundException("Province not found"));

        // Create District entity and set relationships
        DistrictEntity districtEntity = districtMapper.toDistrictEntity(districtDTO);
        districtEntity.setProvince(provinceEntity);

        // Save and return the DTO
        districtEntity = districtRepository.save(districtEntity);
        return districtMapper.toDistrictDTO(districtEntity);
    }

    @Override
    public void removeDistrict(long districtId) {
        DistrictEntity districtEntity = districtRepository.findById(districtId)
                .orElseThrow(() -> new NotFoundException("District not found"));

        districtRepository.delete(districtEntity);
    }

    @Override
    public DistrictDTO updateDistrict(Long districtId, DistrictDTO districtDTO) {
        DistrictEntity districtEntity = districtRepository.findById(districtId)
                .orElseThrow(() -> new NotFoundException("District not found"));

        // Update fields
        districtMapper.updateDistrictEntity(districtDTO, districtEntity);

        // Update province if needed
        if (districtDTO.getProvince() != null) {
            ProvinceEntity provinceEntity = provinceRepository.findById(districtDTO.getProvince().getId())
                    .orElseThrow(() -> new NotFoundException("Province not found"));
            districtEntity.setProvince(provinceEntity);
        }

        districtEntity = districtRepository.save(districtEntity);
        return districtMapper.toDistrictDTO(districtEntity);
    }

    @Override
    public DistrictDTO getDistrict(long districtId) {
        DistrictEntity districtEntity = districtRepository.findById(districtId)
                .orElseThrow(() -> new NotFoundException("District not found"));
        return districtMapper.toDistrictDTO(districtEntity);
    }

    @Override
    public List<DistrictDTO> getDistrictsByProvince(long provinceId) {
        List<DistrictEntity> districts = districtRepository.findByProvinceId(provinceId);
        return districts.stream()
                .map(districtMapper::toDistrictDTO)
                .collect(Collectors.toList());
    }

}
