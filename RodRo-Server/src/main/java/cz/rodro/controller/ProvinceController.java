package cz.rodro.controller;

import cz.rodro.dto.ProvinceDTO;
import cz.rodro.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    // --- Create ---
    @Operation(summary = "Create a new province.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProvinceDTO addProvince(@RequestBody ProvinceDTO provinceDTO) {
        return provinceService.addProvince(provinceDTO);
    }

    // --- Read by ID ---
    @Operation(summary = "Get a province by its ID.")
    @PreAuthorize("permitAll()")
    @GetMapping("/{provinceId}")
    public ProvinceDTO getProvince(@PathVariable long provinceId) {
        return provinceService.getProvince(provinceId);
    }

    // --- Update ---
    @Operation(summary = "Update an existing province.")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{provinceId}")
    public ProvinceDTO updateProvince(@PathVariable long provinceId, @RequestBody ProvinceDTO provinceDTO) {
        return provinceService.updateProvince(provinceId, provinceDTO);
    }

    // --- Delete ---
    @Operation(summary = "Delete a province by its ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{provinceId}")
    public void deleteProvince(@PathVariable long provinceId) {
        provinceService.removeProvince(provinceId);
    }

}
