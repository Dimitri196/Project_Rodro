package cz.rodro.controller;

import cz.rodro.dto.MilitaryArmyBranchDTO;
import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.service.MilitaryArmyBranchService;
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
@RequestMapping("/api") // Request mapping is set back to /api per your example
@RequiredArgsConstructor
@Tag(name = "Military Army Branch", description = "Endpoints for managing military army branches and related ranks.")
public class MilitaryArmyBranchController {

    private final MilitaryArmyBranchService militaryArmyBranchService;

    /**
     * Retrieves a list of all military army branches. Requires no specific authorization.
     * Maps to HTTP GET /api/militaryArmyBranches.
     *
     * @return ResponseEntity containing a List of all MilitaryArmyBranchDTOs and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves a list of all military army branches.")
    @PreAuthorize("permitAll()")
    @GetMapping("/militaryArmyBranches")
    public ResponseEntity<List<MilitaryArmyBranchDTO>> getAll() {
        return ResponseEntity.ok(militaryArmyBranchService.findAll());
    }

    /**
     * Retrieves a military army branch by its unique identifier. Requires no specific authorization.
     * Maps to HTTP GET /api/militaryArmyBranches/{id}.
     *
     * @param id The ID of the army branch.
     * @return ResponseEntity containing the MilitaryArmyBranchDTO and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves a military army branch by ID.")
    @PreAuthorize("permitAll()")
    @GetMapping("/militaryArmyBranches/{id}")
    public ResponseEntity<MilitaryArmyBranchDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(militaryArmyBranchService.findById(id));
    }

    /**
     * Retrieves all ranks associated with organizations belonging to a specific army branch. Requires no specific authorization.
     * Maps to HTTP GET /api/militaryArmyBranches/{id}/ranks.
     *
     * @param id The ID of the Military Army Branch.
     * @return ResponseEntity containing a list of MilitaryRankDTOs and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves all ranks associated with organizations under a specific army branch.")
    @PreAuthorize("permitAll()")
    @GetMapping("/militaryArmyBranches/{id}/ranks")
    public ResponseEntity<List<MilitaryRankDTO>> getRanksByBranchId(@PathVariable Long id) {
        return ResponseEntity.ok(militaryArmyBranchService.getRanksByBranchId(id));
    }

    /**
     * Creates a new military army branch. Requires 'ADMIN' or 'EDITOR' role.
     * Maps to HTTP POST /api/militaryArmyBranches.
     *
     * @param dto The MilitaryArmyBranchDTO containing the data for the new branch.
     * @return ResponseEntity containing the created MilitaryArmyBranchDTO and HttpStatus 201 (Created).
     */
    @Operation(summary = "Creates a new military army branch.")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/militaryArmyBranches")
    public ResponseEntity<MilitaryArmyBranchDTO> add(@Valid @RequestBody MilitaryArmyBranchDTO dto) {
        return new ResponseEntity<>(militaryArmyBranchService.create(dto), HttpStatus.CREATED);
    }

    /**
     * Updates an existing military army branch. Requires 'ADMIN' or 'EDITOR' role.
     * Maps to HTTP PUT /api/militaryArmyBranches/{id}.
     *
     * @param id The ID of the branch to update.
     * @param dto The MilitaryArmyBranchDTO containing the updated data.
     * @return ResponseEntity containing the updated MilitaryArmyBranchDTO and HttpStatus 200 (OK).
     */
    @Operation(summary = "Updates an existing military army branch.")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/militaryArmyBranches/{id}")
    public ResponseEntity<MilitaryArmyBranchDTO> update(@PathVariable Long id, @Valid @RequestBody MilitaryArmyBranchDTO dto) {
        return ResponseEntity.ok(militaryArmyBranchService.update(id, dto));
    }

    /**
     * Deletes a military army branch by its unique identifier. Requires 'ADMIN' role.
     * Maps to HTTP DELETE /api/militaryArmyBranches/{id}.
     *
     * @param id The ID of the branch to delete.
     * @return ResponseEntity with no content and HttpStatus 204 (No Content).
     */
    @Operation(summary = "Deletes a military army branch by ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/militaryArmyBranches/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        militaryArmyBranchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
