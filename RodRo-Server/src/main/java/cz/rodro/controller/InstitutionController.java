package cz.rodro.controller;

import cz.rodro.dto.InstitutionDTO;
import cz.rodro.service.InstitutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Institution resources within the API.
 * Handles HTTP requests for creating, retrieving, updating, and deleting
 * institution records, secured by method-level authorization.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Institutions", description = "Management of historical and current institution records.")
public class InstitutionController {

    private final InstitutionService institutionService;

    /**
     * Creates a new institution record. Requires ADMIN role.
     * Maps to HTTP POST /api/institutions.
     *
     * @param institutionDTO The InstitutionDTO containing the data for the new institution.
     * @return ResponseEntity containing the newly created InstitutionDTO and HttpStatus 201 (CREATED).
     */
    @Operation(summary = "Creates a new institution record (Requires ADMIN role).")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/institutions")
    public ResponseEntity<InstitutionDTO> createInstitution(@RequestBody InstitutionDTO institutionDTO) {
        InstitutionDTO createdInstitution = institutionService.createInstitution(institutionDTO);
        return new ResponseEntity<>(createdInstitution, HttpStatus.CREATED); // 201 Created
    }

    /**
     * Retrieves a list of all institutions. Requires authentication.
     * Maps to HTTP GET /api/institutions.
     *
     * @return ResponseEntity containing a List of all InstitutionDTOs and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves a list of all institutions.")
    @PreAuthorize("permitAll()")
    @GetMapping("/institutions")
    public ResponseEntity<List<InstitutionDTO>> getAllInstitutions() {
        List<InstitutionDTO> institutions = institutionService.getAllInstitutions();
        return ResponseEntity.ok(institutions); // 200 OK
    }

    /**
     * Retrieves a specific institution by its unique ID. Requires authentication.
     * Maps to HTTP GET /api/institutions/{id}.
     *
     * @param id The unique identifier of the institution.
     * @return ResponseEntity containing the requested InstitutionDTO and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves a specific institution by ID.")
    @PreAuthorize("permitAll()")
    @GetMapping("/institutions/{id}")
    public ResponseEntity<InstitutionDTO> getInstitutionById(@PathVariable Long id) {
        InstitutionDTO institution = institutionService.getInstitutionById(id);
        return ResponseEntity.ok(institution); // 200 OK
    }

    /**
     * Updates an existing institution record. Requires ADMIN role.
     * Maps to HTTP PUT /api/institutions/{id}.
     *
     * @param id The unique identifier of the institution to update.
     * @param institutionDTO The InstitutionDTO containing the updated data.
     * @return ResponseEntity containing the updated InstitutionDTO and HttpStatus 200 (OK).
     */
    @Operation(summary = "Updates an existing institution record (Requires ADMIN role).")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/institutions/{id}")
    public ResponseEntity<InstitutionDTO> updateInstitution(
            @PathVariable Long id,
            @RequestBody InstitutionDTO institutionDTO) {
        InstitutionDTO updatedInstitution = institutionService.updateInstitution(id, institutionDTO);
        return ResponseEntity.ok(updatedInstitution); // 200 OK
    }

    /**
     * Deletes an institution record by its unique ID. Requires ADMIN role.
     * Maps to HTTP DELETE /api/institutions/{id}.
     *
     * @param id The unique identifier of the institution to delete.
     * @return ResponseEntity with HttpStatus 204 (NO_CONTENT) indicating successful deletion.
     */
    @Operation(summary = "Deletes an institution record by ID (Requires ADMIN role).")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/institutions/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
        institutionService.deleteInstitution(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}