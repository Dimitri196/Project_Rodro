package cz.rodro.controller;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.service.MilitaryOrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor // Using constructor injection instead of @Autowired
@Tag(name = "Military Organization", description = "Endpoints for managing primary military organizations (e.g., Prussian Army, Imperial Japanese Army).")
public class MilitaryOrganizationController {

    private final MilitaryOrganizationService militaryOrganizationService;

    /**
     * Retrieves a list of all military organizations. Requires no specific authorization.
     * Maps to HTTP GET /api/militaryOrganizations.
     *
     * @return ResponseEntity containing a List of all MilitaryOrganizationDTOs and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves a list of all military organizations.")
    @PreAuthorize("permitAll()")
    @GetMapping("/militaryOrganizations")
    public ResponseEntity<List<MilitaryOrganizationDTO>> getAll() {
        return ResponseEntity.ok(militaryOrganizationService.getAll());
    }

    /**
     * Retrieves a military organization by its unique identifier. Requires no specific authorization.
     * Maps to HTTP GET /api/militaryOrganizations/{id}.
     *
     * @param id The ID of the organization.
     * @return ResponseEntity containing the MilitaryOrganizationDTO and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves a military organization by ID.")
    @PreAuthorize("permitAll()")
    @GetMapping("/militaryOrganizations/{id}")
    public ResponseEntity<MilitaryOrganizationDTO> getMilitaryOrganization(@PathVariable Long id) {
        return ResponseEntity.ok(militaryOrganizationService.getMilitaryOrganization(id));
    }

    /**
     * Creates a new military organization. Requires 'ADMIN' or 'EDITOR' role.
     * Maps to HTTP POST /api/militaryOrganizations.
     *
     * @param dto The MilitaryOrganizationDTO containing the data for the new organization.
     * @return ResponseEntity containing the created MilitaryOrganizationDTO and HttpStatus 201 (Created).
     */
    @Operation(summary = "Creates a new military organization.")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/militaryOrganizations")
    public ResponseEntity<MilitaryOrganizationDTO> add(@Valid @RequestBody MilitaryOrganizationDTO dto) {
        return new ResponseEntity<>(militaryOrganizationService.addMilitaryOrganization(dto), HttpStatus.CREATED);
    }

    /**
     * Updates an existing military organization. Requires 'ADMIN' or 'EDITOR' role.
     * Maps to HTTP PUT /api/militaryOrganizations/{id}.
     *
     * @param id The ID of the organization to update.
     * @param dto The MilitaryOrganizationDTO containing the updated data.
     * @return ResponseEntity containing the updated MilitaryOrganizationDTO and HttpStatus 200 (OK).
     */
    @Operation(summary = "Updates an existing military organization.")
    @PreAuthorize("hasAnyRole('ADMIN)")
    @PutMapping("/militaryOrganizations/{id}")
    public ResponseEntity<MilitaryOrganizationDTO> update(@PathVariable Long id, @Valid @RequestBody MilitaryOrganizationDTO dto) {
        return ResponseEntity.ok(militaryOrganizationService.updateMilitaryOrganization(id, dto));
    }

    /**
     * Deletes a military organization by its unique identifier. Requires 'ADMIN' role.
     * Maps to HTTP DELETE /api/militaryOrganizations/{id}.
     *
     * @param id The ID of the organization to delete.
     * @return ResponseEntity with no content and HttpStatus 204 (No Content).
     */
    @Operation(summary = "Deletes a military organization by ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/militaryOrganizations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        militaryOrganizationService.deleteMilitaryOrganization(id);
        return ResponseEntity.noContent().build();
    }
}
