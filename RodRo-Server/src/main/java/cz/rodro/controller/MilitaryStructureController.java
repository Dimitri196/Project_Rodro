package cz.rodro.controller;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.service.MilitaryStructureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor // Using constructor injection
@Tag(name = "Military Structure", description = "Endpoints for managing military structures (e.g., Division, Regiment).")
public class MilitaryStructureController {

    private final MilitaryStructureService militaryStructureService;

    /**
     * Retrieves a list of all military structures.
     * Maps to HTTP GET /api/militaryStructures.
     *
     * @return ResponseEntity containing a List of all MilitaryStructureDTOs and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves a list of all military structures.")
    @PreAuthorize("permitAll()")
    @GetMapping("/militaryStructures")
    public ResponseEntity<List<MilitaryStructureDTO>> getAll() {
        return ResponseEntity.ok(militaryStructureService.getAll());
    }

    /**
     * Retrieves a military structure by its unique identifier.
     * Maps to HTTP GET /api/militaryStructures/{id}.
     *
     * @param id The ID of the structure.
     * @return ResponseEntity containing the MilitaryStructureDTO and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves a military structure by ID.")
    @PreAuthorize("permitAll()")
    @GetMapping("/militaryStructures/{id}")
    public ResponseEntity<MilitaryStructureDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(militaryStructureService.getMilitaryStructure(id));
    }

    /**
     * Retrieves all ranks directly associated with a specific military structure.
     * Maps to HTTP GET /api/militaryStructures/{id}/ranks.
     *
     * @param id The ID of the military structure.
     * @return ResponseEntity containing a list of MilitaryRankDTOs and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves all ranks specifically assigned to a military structure.")
    @PreAuthorize("permitAll()")
    @GetMapping("/militaryStructures/{id}/ranks")
    public ResponseEntity<List<MilitaryRankDTO>> getRanksForStructure(@PathVariable Long id) {
        return ResponseEntity.ok(militaryStructureService.getRanksForStructure(id));
    }

    /**
     * Creates a new military structure. Requires 'ADMIN' or 'EDITOR' role.
     * Maps to HTTP POST /api/militaryStructures.
     *
     * @param dto The MilitaryStructureDTO containing the data for the new structure.
     * @return ResponseEntity containing the created MilitaryStructureDTO and HttpStatus 201 (Created).
     */
    @Operation(summary = "Creates a new military structure.")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PostMapping("/militaryStructures")
    public ResponseEntity<MilitaryStructureDTO> add(@Valid @RequestBody MilitaryStructureDTO dto) {
        return new ResponseEntity<>(militaryStructureService.addMilitaryStructure(dto), HttpStatus.CREATED);
    }

    /**
     * Updates an existing military structure. Requires 'ADMIN' or 'EDITOR' role.
     * Maps to HTTP PUT /api/militaryStructures/{id}.
     *
     * @param id The ID of the structure to update.
     * @param dto The MilitaryStructureDTO containing the updated data.
     * @return ResponseEntity containing the updated MilitaryStructureDTO and HttpStatus 200 (OK).
     */
    @Operation(summary = "Updates an existing military structure.")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PutMapping("/militaryStructures/{id}")
    public ResponseEntity<MilitaryStructureDTO> update(@PathVariable Long id, @Valid @RequestBody MilitaryStructureDTO dto) {
        return ResponseEntity.ok(militaryStructureService.updateMilitaryStructure(id, dto));
    }

    /**
     * Deletes a military structure by its unique identifier. Requires 'ADMIN' role.
     * Maps to HTTP DELETE /api/militaryStructures/{id}.
     *
     * @param id The ID of the structure to delete.
     * @return ResponseEntity with no content and HttpStatus 204 (No Content).
     */
    @Operation(summary = "Deletes a military structure by ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/militaryStructures/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        militaryStructureService.deleteMilitaryStructure(id);
        return ResponseEntity.noContent().build();
    }

}
