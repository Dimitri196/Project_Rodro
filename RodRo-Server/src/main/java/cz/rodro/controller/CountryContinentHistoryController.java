package cz.rodro.controller;

import cz.rodro.dto.CountryContinentHistoryDTO;
import cz.rodro.service.CountryContinentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/country-continent-history")
@Tag(name = "Historical Geography", description = "Endpoints for managing the temporal association between countries and continents.")
public class CountryContinentHistoryController {

    private final CountryContinentHistoryService historyService;

    public CountryContinentHistoryController(CountryContinentHistoryService historyService) {
        this.historyService = historyService;
    }

    // --- READ ENDPOINTS (Public Access) ---

    @Operation(summary = "Get all continent history records for a specific country.")
    @PreAuthorize("permitAll()")
    @GetMapping("/country/{countryId}") // Maps to /api/country-continent-history/country/123
    public ResponseEntity<List<CountryContinentHistoryDTO>> getHistoryByCountry(@PathVariable long countryId) {
        return ResponseEntity.ok(historyService.getHistoryByCountry(countryId));
    }

    // --- WRITE ENDPOINTS (Admin Only) ---

    @Operation(summary = "Create a new temporal continent association record.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping // Maps to /api/country-continent-history
    public ResponseEntity<CountryContinentHistoryDTO> addHistoryRecord(@Valid @RequestBody CountryContinentHistoryDTO dto) {
        CountryContinentHistoryDTO createdRecord = historyService.addHistoryRecord(dto);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing temporal continent association record (e.g., changing endYear).")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{historyId}")
    public ResponseEntity<CountryContinentHistoryDTO> updateHistoryRecord(
            @PathVariable long historyId,
            @Valid @RequestBody CountryContinentHistoryDTO dto) {
        CountryContinentHistoryDTO updatedRecord = historyService.updateHistoryRecord(historyId, dto);
        return ResponseEntity.ok(updatedRecord);
    }

    @Operation(summary = "Delete a temporal continent association record.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{historyId}")
    public ResponseEntity<Void> deleteHistoryRecord(@PathVariable long historyId) {
        historyService.deleteHistoryRecord(historyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
