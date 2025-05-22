package cz.rodro.controller;

import cz.rodro.dto.CemeteryDTO;
import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.ParishLocationDTO;
import cz.rodro.service.CemeteryService;
import cz.rodro.service.LocationService;
import cz.rodro.service.ParishLocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController {

    private final LocationService locationService;
    private final CemeteryService cemeteryService;
    private final ParishLocationService parishLocationService;

    public LocationController(LocationService locationService,
                              CemeteryService cemeteryService,
                              ParishLocationService parishLocationService) {
        this.locationService = locationService;
        this.cemeteryService = cemeteryService;
        this.parishLocationService = parishLocationService;
    }

    @PostMapping("/locations")
    public LocationDTO addLocation(@RequestBody LocationDTO locationDTO) {
        return locationService.addLocation(locationDTO);
    }

    @GetMapping("/locations/{locationId}")
    public LocationDTO getLocation(@PathVariable Long locationId) {
        return locationService.getLocation(locationId);
    }

    @PutMapping({"/locations/{locationId}", "/locations/{locationId}/"})
    public LocationDTO updateLocation(@PathVariable Long locationId, @RequestBody LocationDTO locationDTO) {
        return locationService.updateLocation(locationId, locationDTO);
    }

    @DeleteMapping({"/locations/{locationId}", "/locations/{locationId}/"})
    public LocationDTO deleteLocation(@PathVariable Long locationId) {
        return locationService.removeLocation(locationId);
    }

    @GetMapping("/locations")
    public List<LocationDTO> getAllLocations() {
        return locationService.getAll();
    }

    @GetMapping("/locations/{locationId}/cemeteries")
    public List<CemeteryDTO> getCemeteriesByLocation(@PathVariable Long locationId) {
        return cemeteryService.getCemeteriesByLocationId(locationId);
    }

    @GetMapping("/locations/{locationId}/parishes")
    public List<ParishLocationDTO> getParishesByLocation(@PathVariable Long locationId) {
        return parishLocationService.getParishesByLocationId(locationId);
    }

    @PostMapping("/locations/{locationId}/parishes")
    public ParishLocationDTO addParishToLocation(@PathVariable Long locationId, @RequestBody ParishLocationDTO parishLocationDTO) {
        return parishLocationService.addParishToLocation(locationId, parishLocationDTO);
    }

    @DeleteMapping("/locations/{locationId}/parishes/{parishId}")
    public void removeParishFromLocation(@PathVariable Long locationId, @PathVariable Long parishId) {
        parishLocationService.removeParishFromLocation(locationId, parishId);
    }
}