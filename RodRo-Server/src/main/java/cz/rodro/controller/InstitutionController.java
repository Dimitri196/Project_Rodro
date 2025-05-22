package cz.rodro.controller;

import cz.rodro.dto.InstitutionDTO;
import cz.rodro.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    /**
     * Creates a new institution.
     */
    @PostMapping("/institutions")
    public InstitutionDTO createInstitution(@RequestBody InstitutionDTO institutionDTO) {
        return institutionService.createInstitution(institutionDTO);
    }

    /**
     * Retrieves all institutions.
     */
    @GetMapping("/institutions")
    public List<InstitutionDTO> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    /**
     * Retrieves an institution by ID.
     */
    @GetMapping("/institutions/{id}")
    public InstitutionDTO getInstitutionById(@PathVariable Long id) {
        return institutionService.getInstitutionById(id);
    }

    /**
     * Updates an existing institution.
     */
    @PutMapping("/institutions/{id}")
    public InstitutionDTO updateInstitution(
            @PathVariable Long id,
            @RequestBody InstitutionDTO institutionDTO) {
        return institutionService.updateInstitution(id, institutionDTO);
    }

    /**
     * Deletes an institution by ID.
     */
    @DeleteMapping("/institutions/{id}")
    public void deleteInstitution(@PathVariable Long id) {
        institutionService.deleteInstitution(id);
    }
}

