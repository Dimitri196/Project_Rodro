package cz.rodro.controller;

import cz.rodro.dto.ContinentDTO;
import cz.rodro.service.ContinentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/continents")
@Tag(name = "Continent Management", description = "Endpoints for managing the static list of geographical continents.")
public class ContinentController {

    private final ContinentService continentService;

    public ContinentController(ContinentService continentService) {
        this.continentService = continentService;
    }

    @Operation(summary = "Get a list of all continents.")
    @PreAuthorize("permitAll()")
    @GetMapping // Maps to /api/continents
    public ResponseEntity<List<ContinentDTO>> getAllContinents() {
        return ResponseEntity.ok(continentService.getAllContinents());
    }

    @Operation(summary = "Get a single continent by ID.")
    @PreAuthorize("permitAll()")
    @GetMapping("/{continentId}")
    public ResponseEntity<ContinentDTO> getContinent(@PathVariable long continentId) {
        return ResponseEntity.ok(continentService.getContinent(continentId));
    }

    @Operation(summary = "Create a new continent.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ContinentDTO> addContinent(@Valid @RequestBody ContinentDTO continentDTO) {
        ContinentDTO createdContinent = continentService.addContinent(continentDTO);
        return new ResponseEntity<>(createdContinent, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing continent.")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{continentId}")
    public ResponseEntity<ContinentDTO> updateContinent(
            @PathVariable long continentId,
            @Valid @RequestBody ContinentDTO continentDTO) {
        ContinentDTO updatedContinent = continentService.updateContinent(continentId, continentDTO);
        return ResponseEntity.ok(updatedContinent);
    }

    @Operation(summary = "Delete a continent by ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{continentId}")
    public ResponseEntity<Void> deleteContinent(@PathVariable long continentId) {
        continentService.deleteContinent(continentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
