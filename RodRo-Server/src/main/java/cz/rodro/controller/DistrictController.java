package cz.rodro.controller;

import cz.rodro.dto.DistrictDTO;
import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.LocationHistoryDTO;
import cz.rodro.service.DistrictService;
import cz.rodro.service.LocationHistoryService;
import cz.rodro.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/districts") // Base URL: /api/districts
@Tag(name = "District Management", description = "Endpoints for handling Districts.")
public class DistrictController {

    private final DistrictService districtService;

    private final LocationHistoryService locationHistoryService;

    public DistrictController(DistrictService districtService, LocationHistoryService locationHistoryService) {
        this.districtService = districtService;
        this.locationHistoryService = locationHistoryService;
    }

    // -----------------------------------------------------------
    // --- District Retrieval (Single District) ---
    // -----------------------------------------------------------

    @Operation(summary = "Get a specific district by its ID.")
    @PreAuthorize("permitAll()")
    @GetMapping("/{districtId}") // Maps to: /api/districts/{districtId}
    public ResponseEntity<DistrictDTO> getDistrictById(@PathVariable long districtId) {

        // This is the endpoint that the frontend DistrictDetail component should now call.
        return ResponseEntity.ok(districtService.getDistrict(districtId));
    }

    // -----------------------------------------------------------
    // --- District Retrieval (List by Province) ---
    // -----------------------------------------------------------

    // NOTE: This endpoint is not directly called by the frontend component you showed,
    // but it replaces the original /api/countries/{id}/provinces/{id}/districts mapping.
    @Operation(summary = "Get all districts for a specific province.")
    @PreAuthorize("permitAll()")
    @GetMapping("/provinces/{provinceId}") // Maps to: /api/districts/provinces/{provinceId}
    public ResponseEntity<List<DistrictDTO>> getDistrictsByProvince(@PathVariable long provinceId) {
        return ResponseEntity.ok(districtService.getDistrictsByProvince(provinceId));
    }

    @Operation(summary = "Get all unique locations currently or historically associated with a specific district.")
    @PreAuthorize("permitAll()")
    // 3. Add the required mapping: /api/districts/{districtId}/locations
    @GetMapping("/{districtId}/locations")
    public ResponseEntity<List<LocationDTO>> getLocationsInDistrict(@PathVariable long districtId) {

        // 4. Call the service method to retrieve and map the LocationDTOs
        List<LocationDTO> locations = locationHistoryService.getLocationsByDistrictId(districtId);

        return ResponseEntity.ok(locations);
    }

}
