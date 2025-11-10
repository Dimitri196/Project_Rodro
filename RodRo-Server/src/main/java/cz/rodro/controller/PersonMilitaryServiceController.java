package cz.rodro.controller;

import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.service.PersonMilitaryService;
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
@Tag(name = "Person Military Service", description = "Endpoints for managing records linking a Person to their Rank and Structure.")
public class PersonMilitaryServiceController {

    private final PersonMilitaryService personMilitaryService;

    // --- GET (Read) Operations ---

    /**
     * Retrieves a list of all military service records, or filters them by a specific person ID.
     * Requires the user to be authenticated.
     * Maps to HTTP GET /api/personMilitaryServices?personId={id}.
     *
     * @param personId Optional ID of the person to filter records by.
     * @return ResponseEntity containing a List of PersonMilitaryServiceDTOs and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves all military service records, optionally filtered by Person ID.")
    @PreAuthorize("isAuthenticated()") // Restrict read access to authenticated users
    @GetMapping("/personMilitaryServices")
    public ResponseEntity<List<PersonMilitaryServiceDTO>> getAllOrByPersonId(
            @RequestParam(required = false) Long personId) {
        if (personId != null) {
            return ResponseEntity.ok(personMilitaryService.getByPersonId(personId));
        }
        return ResponseEntity.ok(personMilitaryService.getAll());
    }

    /**
     * Retrieves a specific military service record by its unique identifier.
     * Requires the user to be authenticated.
     * Maps to HTTP GET /api/personMilitaryServices/{id}.
     *
     * @param id The ID of the service record.
     * @return ResponseEntity containing the PersonMilitaryServiceDTO and HttpStatus 200 (OK).
     */
    @Operation(summary = "Retrieves a military service record by ID.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/personMilitaryServices/{id}")
    public ResponseEntity<PersonMilitaryServiceDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(personMilitaryService.getPersonMilitaryService(id));
    }

    // --- POST (Create) Operation ---

    /**
     * Creates a new military service record. Requires 'ADMIN' or 'EDITOR' role.
     * Maps to HTTP POST /api/personMilitaryServices.
     *
     * @param dto The PersonMilitaryServiceDTO containing the data for the new record.
     * @return ResponseEntity containing the created PersonMilitaryServiceDTO and HttpStatus 201 (Created).
     */
    @Operation(summary = "Creates a new military service record.")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PostMapping("/personMilitaryServices")
    public ResponseEntity<PersonMilitaryServiceDTO> add(@Valid @RequestBody PersonMilitaryServiceDTO dto) {
        return new ResponseEntity<>(personMilitaryService.addPersonMilitaryService(dto), HttpStatus.CREATED);
    }

    // --- PUT (Update) Operation ---

    /**
     * Updates an existing military service record. Requires 'ADMIN' or 'EDITOR' role.
     * Maps to HTTP PUT /api/personMilitaryServices/{id}.
     *
     * @param id The ID of the record to update.
     * @param dto The PersonMilitaryServiceDTO containing the updated data.
     * @return ResponseEntity containing the updated PersonMilitaryServiceDTO and HttpStatus 200 (OK).
     */
    @Operation(summary = "Updates an existing military service record.")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PutMapping("/personMilitaryServices/{id}")
    public ResponseEntity<PersonMilitaryServiceDTO> update(@PathVariable Long id, @Valid @RequestBody PersonMilitaryServiceDTO dto) {
        return ResponseEntity.ok(personMilitaryService.updatePersonMilitaryService(id, dto));
    }

    // --- DELETE Operation ---

    /**
     * Deletes a military service record by its unique identifier. Requires 'ADMIN' role.
     * Maps to HTTP DELETE /api/personMilitaryServices/{id}.
     *
     * @param id The ID of the record to delete.
     * @return ResponseEntity with no content and HttpStatus 204 (No Content).
     */
    @Operation(summary = "Deletes a military service record by ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/personMilitaryServices/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personMilitaryService.removePersonMilitaryService(id);
        return ResponseEntity.noContent().build();
    }
}
