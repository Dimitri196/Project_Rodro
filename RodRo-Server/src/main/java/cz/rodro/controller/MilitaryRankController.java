package cz.rodro.controller;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.service.MilitaryRankService;
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
@RequiredArgsConstructor // Using constructor injection instead of @Autowired
@Tag(name = "Military Rank", description = "Endpoints for managing military ranks and associated personnel.")
public class MilitaryRankController {

    private final MilitaryRankService militaryRankService;

    // --- GET (Read) Operations ---

    /**
     * Retrieves a list of all military ranks.
     * Maps to HTTP GET /api/militaryRanks.
     *
     * @return ResponseEntity containing a List of all MilitaryRankDTOs and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves a list of all military ranks.")
    @PreAuthorize("permitAll()")
    @GetMapping("/militaryRanks")
    public ResponseEntity<List<MilitaryRankDTO>> getAll() {
        return ResponseEntity.ok(militaryRankService.getAll());
    }

    /**
     * Retrieves a military rank by its unique identifier, including all associated personnel records.
     * Maps to HTTP GET /api/militaryRanks/{id}.
     *
     * @param id The ID of the rank.
     * @return ResponseEntity containing the MilitaryRankDTO with persons list populated, and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves a military rank by ID, eagerly fetching associated personnel records.")
    @PreAuthorize("permitAll()")
    @GetMapping("/militaryRanks/{id}")
    public ResponseEntity<MilitaryRankDTO> getById(@PathVariable Long id) {
        // NOTE: Using getMilitaryRankWithPersons() to eagerly load the associated personnel collection.
        return ResponseEntity.ok(militaryRankService.getMilitaryRankWithPersons(id));
    }

    // --- POST (Create) Operation ---

    /**
     * Creates a new military rank. Requires 'ADMIN' or 'EDITOR' role.
     * Maps to HTTP POST /api/militaryRanks.
     *
     * @param dto The MilitaryRankDTO containing the data for the new rank.
     * @return ResponseEntity containing the created MilitaryRankDTO and HttpStatus 201 (Created).
     */
    @Operation(summary = "Creates a new military rank.")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PostMapping("/militaryRanks")
    public ResponseEntity<MilitaryRankDTO> add(@Valid @RequestBody MilitaryRankDTO dto) {
        return new ResponseEntity<>(militaryRankService.addMilitaryRank(dto), HttpStatus.CREATED);
    }

    // --- PUT (Update) Operation ---

    /**
     * Updates an existing military rank. Requires 'ADMIN' or 'EDITOR' role.
     * Maps to HTTP PUT /api/militaryRanks/{id}.
     *
     * @param id The ID of the rank to update.
     * @param dto The MilitaryRankDTO containing the updated data.
     * @return ResponseEntity containing the updated MilitaryRankDTO and HttpStatus 200 (OK).
     */
    @Operation(summary = "Updates an existing military rank.")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PutMapping("/militaryRanks/{id}")
    public ResponseEntity<MilitaryRankDTO> update(@PathVariable Long id, @Valid @RequestBody MilitaryRankDTO dto) {
        return ResponseEntity.ok(militaryRankService.updateMilitaryRank(id, dto));
    }

    // --- DELETE Operation ---

    /**
     * Deletes a military rank by its unique identifier. Requires 'ADMIN' role.
     * Maps to HTTP DELETE /api/militaryRanks/{id}.
     *
     * @param id The ID of the rank to delete.
     * @return ResponseEntity with no content and HttpStatus 204 (No Content).
     */
    @Operation(summary = "Deletes a military rank by ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/militaryRanks/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Corrected service method name to match the established service interface (deleteMilitaryRank)
        militaryRankService.deleteMilitaryRank(id);
        return ResponseEntity.noContent().build();
    }
}
