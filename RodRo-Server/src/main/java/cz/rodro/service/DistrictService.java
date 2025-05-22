package cz.rodro.service;

import cz.rodro.dto.DistrictDTO;

import java.util.List;

public interface DistrictService {

    DistrictDTO getDistrict(long districtId);
    DistrictDTO addDistrict(DistrictDTO districtDTO);
    void removeDistrict(long districtId);

    DistrictDTO updateDistrict(Long distictId, DistrictDTO districtDTO);

    List<DistrictDTO> getDistrictsByProvince(long provinceId);

}
