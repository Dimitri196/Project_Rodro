package cz.rodro.service;

import cz.rodro.dto.ProvinceDTO;

import java.util.List;

public interface ProvinceService {

    ProvinceDTO addProvince(ProvinceDTO provinceDTO);

    ProvinceDTO getProvince(long provinceId);

    void removeProvince(long provinceId);

    ProvinceDTO updateProvince(Long provinceId, ProvinceDTO provinceDTO);

    public List<ProvinceDTO> getProvincesByCountry(long countryId);
}
