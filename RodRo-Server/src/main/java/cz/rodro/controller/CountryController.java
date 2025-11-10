package cz.rodro.controller;

import cz.rodro.dto.CountryContinentHistoryDTO;
import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.ProvinceDTO;
import cz.rodro.service.CountryContinentHistoryService;
import cz.rodro.service.CountryService;
import cz.rodro.service.ProvinceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@Tag(name = "Country Management", description = "Endpoints for handling Countries and their nested geographical data.")
public class CountryController {

    private final CountryService countryService;
    private final ProvinceService provinceService;
    private final CountryContinentHistoryService historyService;

    public CountryController(
            CountryService countryService,
            ProvinceService provinceService,
            CountryContinentHistoryService historyService) {
        this.countryService = countryService;
        this.provinceService = provinceService;
        this.historyService = historyService;
    }

    // ------------------------------------
    // --- 1. CORE COUNTRY ENDPOINTS ---
    // ------------------------------------

    @Operation(summary = "Get a list of all countries.")
    @PreAuthorize("permitAll()")
    @GetMapping // Maps to /api/countries
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAll());
    }

    @Operation(summary = "Create a new country.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CountryDTO> addCountry(@Valid @RequestBody CountryDTO countryDTO) {
        return new ResponseEntity<>(countryService.addCountry(countryDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a country by ID, including its provinces and continent history.")
    @PreAuthorize("permitAll()")
    @GetMapping("/{countryId}")
    public ResponseEntity<CountryDTO> getCountry(@PathVariable long countryId) {
        return ResponseEntity.ok(countryService.getCountry(countryId));
    }

    @Operation(summary = "Update an existing country.")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{countryId}")
    public ResponseEntity<CountryDTO> updateCountry(
            @PathVariable long countryId,
            @Valid @RequestBody CountryDTO countryDTO) {
        return ResponseEntity.ok(countryService.updateCountry(countryId, countryDTO));
    }

    // ------------------------------------
    // --- 2. HISTORY ENDPOINTS (Nested) ---
    // ------------------------------------

    @Operation(summary = "Get all continent history records for a specific country.")
    @PreAuthorize("permitAll()")
    @GetMapping("/{countryId}/continent-history")
    public ResponseEntity<List<CountryContinentHistoryDTO>> getHistoryByCountry(
            @PathVariable long countryId) {
        return ResponseEntity.ok(historyService.getHistoryByCountry(countryId));
    }

    @Operation(summary = "Create a new continent history record for a country.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{countryId}/continent-history")
    public ResponseEntity<CountryContinentHistoryDTO> addHistoryToCountry(
            @PathVariable long countryId,
            @Valid @RequestBody CountryContinentHistoryDTO historyDTO) {

        historyDTO.setCountryId(countryId);
        return new ResponseEntity<>(historyService.addHistoryRecord(historyDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing continent history record.")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{countryId}/continent-history/{historyId}")
    public ResponseEntity<CountryContinentHistoryDTO> updateHistoryFromCountry(
            @PathVariable long countryId,
            @PathVariable long historyId,
            @Valid @RequestBody CountryContinentHistoryDTO historyDTO) {

        historyDTO.setCountryId(countryId);
        return ResponseEntity.ok(historyService.updateHistoryRecord(historyId, historyDTO));
    }

    @Operation(summary = "Delete a specific continent history record.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{countryId}/continent-history/{historyId}")
    public ResponseEntity<Void> deleteHistoryFromCountry(
            @PathVariable long countryId,
            @PathVariable long historyId) {

        historyService.deleteHistoryRecord(historyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
