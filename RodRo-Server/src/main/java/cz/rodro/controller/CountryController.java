package cz.rodro.controller;

import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.DistrictDTO;
import cz.rodro.dto.ProvinceDTO;
import cz.rodro.service.CountryService;
import cz.rodro.service.DistrictService;
import cz.rodro.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DistrictService districtService;

    // Country endpoints
    @GetMapping("/countries")
    public List<CountryDTO> getAll() {
        return countryService.getAll();
    }

    @PostMapping("/countries")
    public CountryDTO addCountry(@RequestBody CountryDTO countryDTO) {
        return countryService.addCountry(countryDTO);
    }

    @GetMapping("/countries/{countryId}")
    public CountryDTO getCountry(@PathVariable long countryId) {
        CountryDTO countryDTO = countryService.getCountry(countryId);
        System.out.println("Country DTO: " + countryDTO);  // Log the returned countryDTO
        return countryDTO;
    }

    @PutMapping("/countries/{countryId}")
    public CountryDTO updateCountry(@PathVariable long countryId, @RequestBody CountryDTO countryDTO) {
        return countryService.updateCountry(countryId, countryDTO);
    }

    // Province endpoints integrated with CountryController
    @PostMapping("/countries/{countryId}/provinces")
    public ProvinceDTO addProvinceToCountry(@PathVariable long countryId, @RequestBody ProvinceDTO provinceDTO) {
        // Create an empty CountryDTO and set the countryId
        CountryDTO countryDTO = new CountryDTO();  // Empty countryDTO
        countryDTO.setId(countryId);  // Set the countryId

        // Set the country reference to the province
        provinceDTO.setCountry(countryDTO);

        // Add the province to the country
        return provinceService.addProvince(provinceDTO);
    }

    @GetMapping("/countries/{countryId}/provinces/{provinceId}")
    public ProvinceDTO getProvinceFromCountry(@PathVariable long countryId, @PathVariable long provinceId) {
        return provinceService.getProvince(provinceId);
    }

    @PutMapping("/countries/{countryId}/provinces/{provinceId}")
    public ProvinceDTO updateProvinceFromCountry(@PathVariable long countryId, @PathVariable long provinceId, @RequestBody ProvinceDTO provinceDTO) {
        // Set the country reference using the countryId
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(countryId);
        provinceDTO.setCountry(countryDTO);

        return provinceService.updateProvince(provinceId, provinceDTO);
    }

    @DeleteMapping("/countries/{countryId}/provinces/{provinceId}")
    public void deleteProvinceFromCountry(@PathVariable long countryId, @PathVariable long provinceId) {
        provinceService.removeProvince(provinceId);
    }

    // Districts endpoints integrated with ProvinceController
    @GetMapping("/countries/{countryId}/provinces/{provinceId}/districts")
    public List<DistrictDTO> getDistrictsByProvince(@PathVariable long countryId, @PathVariable long provinceId) {
        return districtService.getDistrictsByProvince(provinceId);
    }

    @GetMapping("/countries/{countryId}/provinces/{provinceId}/districts/{districtId}")
    public DistrictDTO getDistrictById(@PathVariable long countryId, @PathVariable long provinceId, @PathVariable long districtId) {
        return districtService.getDistrict(districtId);
    }

    @GetMapping("/countries/{countryId}/provinces")
    public List<ProvinceDTO> getProvincesByCountry(@PathVariable long countryId) {
        return provinceService.getProvincesByCountry(countryId);
    }
}
