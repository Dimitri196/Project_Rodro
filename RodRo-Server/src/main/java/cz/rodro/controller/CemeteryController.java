package cz.rodro.controller;

import cz.rodro.dto.CemeteryDTO;
import cz.rodro.service.CemeteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CemeteryController {

    @Autowired
    private CemeteryService cemeteryService;

    // Get all cemeteries
    @GetMapping("/cemeteries")
    public List<CemeteryDTO> getAllCemeteries() {
        return cemeteryService.getAllCemeteries();
    }

    // Get cemetery by ID
    @GetMapping("/cemeteries/{cemeteryId}")
    public CemeteryDTO getCemeteryById(@PathVariable long cemeteryId) {
        return cemeteryService.getCemeteryById(cemeteryId);
    }

    // Add a new cemetery
    @Secured("ROLE_ADMIN")
    @PostMapping("/cemeteries")
    public CemeteryDTO addCemetery(@RequestBody CemeteryDTO cemeteryDTO) {
        return cemeteryService.addCemetery(cemeteryDTO);
    }

    // Update an existing cemetery
    @Secured("ROLE_ADMIN")
    @PutMapping("/cemeteries/{cemeteryId}")
    public CemeteryDTO updateCemetery(@PathVariable long cemeteryId, @RequestBody CemeteryDTO cemeteryDTO) {
        return cemeteryService.updateCemetery(cemeteryId, cemeteryDTO);
    }

    // Delete a cemetery
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/cemeteries/{cemeteryId}")
    public void deleteCemetery(@PathVariable long cemeteryId) {
        cemeteryService.deleteCemetery(cemeteryId);
    }
}