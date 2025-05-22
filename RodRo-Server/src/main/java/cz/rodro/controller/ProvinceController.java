package cz.rodro.controller;

import cz.rodro.dto.ProvinceDTO;
import cz.rodro.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @PostMapping
    public ProvinceDTO addProvince(@RequestBody ProvinceDTO provinceDTO) {
        return provinceService.addProvince(provinceDTO);
    }

    @GetMapping("/{provinceId}")
    public ProvinceDTO getProvince(@PathVariable long provinceId) {
        return provinceService.getProvince(provinceId);
    }

    @PutMapping("/{provinceId}")
    public ProvinceDTO updateProvince(@PathVariable long provinceId, @RequestBody ProvinceDTO provinceDTO) {
        return provinceService.updateProvince(provinceId, provinceDTO);
    }

    @DeleteMapping("/{provinceId}")
    public void deleteProvince(@PathVariable long provinceId) {
        provinceService.removeProvince(provinceId);
    }
}
