package cz.rodro.controller;

import cz.rodro.dto.*;
import cz.rodro.service.CemeteryService;
import cz.rodro.service.LocationService;
import cz.rodro.service.ParishLocationService;
import cz.rodro.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController {

    private final LocationService locationService;
    private final CemeteryService cemeteryService;
    private final ParishLocationService parishLocationService;

    private final PersonService personService;

    public LocationController(LocationService locationService,
                              CemeteryService cemeteryService,
                              ParishLocationService parishLocationService, PersonService personService) {
        this.locationService = locationService;
        this.cemeteryService = cemeteryService;
        this.parishLocationService = parishLocationService;
        this.personService = personService;
    }

    /**
     * Adds a new location. Accessible only by ADMIN role.
     * @param locationDTO The DTO containing location data.
     * @return ResponseEntity with the created LocationDTO and HTTP status 201 Created.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/locations")
    public ResponseEntity<LocationDTO> addLocation(@Valid @RequestBody LocationDTO locationDTO) {
        LocationDTO createdLocation = locationService.addLocation(locationDTO);
        return ResponseEntity.created(URI.create("/api/locations/" + createdLocation.getId())).body(createdLocation);
    }

    /**
     * Retrieves a single location by its ID.
     * @param locationId The ID of the location.
     * @return The LocationDTO.
     */
    @GetMapping("/locations/{locationId}")
    public LocationDTO getLocation(@PathVariable Long locationId) {
        return locationService.getLocation(locationId);
    }

    /**
     * Updates an existing location. Accessible only by ADMIN role.
     * @param locationId The ID of the location to update.
     * @param locationDTO The DTO containing updated location data.
     * @return The updated LocationDTO.
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/locations/{locationId}")
    public LocationDTO updateLocation(@PathVariable Long locationId, @Valid @RequestBody LocationDTO locationDTO) {
        // Ensure the ID in the path matches the ID in the DTO, if provided in DTO
        if (locationDTO.getId() != null && !locationId.equals(locationDTO.getId())) {
            // You might want to throw an IllegalArgumentException or handle this more gracefully
            // For now, let's proceed with the path variable ID as the definitive one.
            locationDTO.setId(locationId);
        }
        return locationService.updateLocation(locationId, locationDTO);
    }

    /**
     * Deletes a location by its ID. Accessible only by ADMIN role.
     * @param locationId The ID of the location to delete.
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/locations/{locationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Returns 204 No Content on successful deletion
    public void deleteLocation(@PathVariable Long locationId) {
        locationService.removeLocation(locationId);
    }

    /**
     * Retrieves a paginated, filtered, and sorted list of locations.
     * Returns a Page of LocationListProjection for optimized performance.
     *
     * @param page The page number (0-indexed, default 0).
     * @param size The number of items per page (default 10).
     * @param sortBy The field to sort by (default "locationName").
     * @param sortOrder The sort order ("asc" or "desc", default "asc").
     * @param searchTerm Optional search term for filtering by location name.
     * @return A Page object containing LocationListProjection DTOs.
     */
    @GetMapping("/locations")
    public ResponseEntity<Page<LocationListProjection>> getAllLocations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "locationName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String searchTerm) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder.toUpperCase()), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<LocationListProjection> locationsPage = locationService.getAllLocations(searchTerm, pageable);
        return ResponseEntity.ok(locationsPage);
    }

    /**
     * Retrieves all cemeteries associated with a specific location.
     * @param locationId The ID of the location.
     * @return A list of CemeteryDTOs.
     */
    @GetMapping("/locations/{locationId}/cemeteries")
    public List<CemeteryDTO> getCemeteriesByLocation(@PathVariable Long locationId) {
        return cemeteryService.getCemeteriesByLocationId(locationId);
    }

    /**
     * Retrieves all parishes associated with a specific location.
     * @param locationId The ID of the location.
     * @return A list of ParishLocationDTOs.
     */
    @GetMapping("/locations/{locationId}/parishes")
    public List<ParishLocationDTO> getParishesByLocation(@PathVariable Long locationId) {
        return parishLocationService.getParishesByLocationId(locationId);
    }

    /**
     * Adds a parish to a specific location. Accessible only by ADMIN role.
     * @param locationId The ID of the location.
     * @param parishLocationDTO The DTO containing parish location data.
     * @return The created ParishLocationDTO.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/locations/{locationId}/parishes")
    public ParishLocationDTO addParishToLocation(@PathVariable Long locationId, @RequestBody ParishLocationDTO parishLocationDTO) {
        return parishLocationService.addParishToLocation(locationId, parishLocationDTO);
    }

    /**
     * Removes a parish from a specific location. Accessible only by ADMIN role.
     * @param locationId The ID of the location.
     * @param parishId The ID of the parish to remove.
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/locations/{locationId}/parishes/{parishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Returns 204 No Content on successful deletion
    public void removeParishFromLocation(@PathVariable Long locationId, @PathVariable Long parishId) {
        parishLocationService.removeParishFromLocation(locationId, parishId);
    }

    // -------------------------------------------------------------------------
    // --- NEW ENDPOINTS FOR REVERSE GENEALOGICAL QUERIES (PERSON EVENTS) ---
    // -------------------------------------------------------------------------

    /**
     * Retrieves all persons born at the specified location.
     * Maps to frontend endpoint: /api/locations/{id}/births
     * @param locationId The ID of the birth location.
     * @return A list of PersonInLocationDTOs.
     */
    @GetMapping("/locations/{locationId}/births")
    public List<PersonInLocationDTO> getPeopleBornAtLocation(@PathVariable Long locationId) {
        // Assuming PersonService has a method that fetches the required person data (id, name, year)
        return personService.findPeopleByEvent(locationId, "births");
    }

    /**
     * Retrieves all persons who died at the specified location.
     * Maps to frontend endpoint: /api/locations/{id}/deaths
     * @param locationId The ID of the death location.
     * @return A list of PersonInLocationDTOs.
     */
    @GetMapping("/locations/{locationId}/deaths")
    public List<PersonInLocationDTO> getPeopleDiedAtLocation(@PathVariable Long locationId) {
        return personService.findPeopleByEvent(locationId, "deaths");
    }

    /**
     * Retrieves all persons buried at the specified location.
     * Maps to frontend endpoint: /api/locations/{id}/burials
     * @param locationId The ID of the burial location.
     * @return A list of PersonInLocationDTOs.
     */
    @GetMapping("/locations/{locationId}/burials")
    public List<PersonInLocationDTO> getPeopleBuriedAtLocation(@PathVariable Long locationId) {
        return personService.findPeopleByEvent(locationId, "burials");
    }

    /**
     * Retrieves all persons baptised at the specified location.
     * Maps to frontend endpoint: /api/locations/{id}/baptisms
     * @param locationId The ID of the baptism location.
     * @return A list of PersonInLocationDTOs.
     */
    @GetMapping("/locations/{locationId}/baptisms")
    public List<PersonInLocationDTO> getPeopleBaptisedAtLocation(@PathVariable Long locationId) {
        return personService.findPeopleByEvent(locationId, "baptisms");
    }




}
