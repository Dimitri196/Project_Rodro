package cz.rodro.controller;

import cz.rodro.dto.SourceDTO;
import cz.rodro.dto.SourceListProjection;
import cz.rodro.service.SourceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/sources")
public class SourceController {

    private final SourceService sourceService;

    @Autowired
    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    /**
     * Creates a new source. Accessible only by ADMIN role.
     * @param sourceDTO The DTO containing source data.
     * @return ResponseEntity with the created SourceDTO and HTTP status 201 Created.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<SourceDTO> addSource(@Valid @RequestBody SourceDTO sourceDTO) {
        SourceDTO createdSource = sourceService.addSource(sourceDTO);
        return ResponseEntity.created(URI.create("/api/sources/" + createdSource.getId())).body(createdSource);
    }

    /**
     * Retrieves a paginated, filtered, and sorted list of sources.
     * Returns a Page of SourceListProjection for optimized performance.
     *
     * @param page The page number (0-indexed, default 0).
     * @param size The number of items per page (default 10).
     * @param sortBy The field to sort by (default "sourceTitle").
     * @param sortOrder The sort order ("asc" or "desc", default "asc").
     * @param searchTerm Optional search term for filtering sources.
     * @return A Page object containing SourceListProjection DTOs.
     */
    @GetMapping
    public ResponseEntity<Page<SourceListProjection>> getAllSources(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "sourceTitle") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String searchTerm) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder.toUpperCase()), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SourceListProjection> sourcesPage = sourceService.getAllSources(searchTerm, pageable);
        return ResponseEntity.ok(sourcesPage);
    }

    /**
     * Retrieves a specific source by ID.
     * @param sourceId The ID of the source.
     * @return The SourceDTO.
     */
    @GetMapping("/{sourceId}")
    public SourceDTO getSource(@PathVariable Long sourceId) {
        return sourceService.getSource(sourceId);
    }

    /**
     * Updates an existing source. Accessible only by ADMIN role.
     * @param sourceId The ID of the source to update.
     * @param sourceDTO The DTO containing updated source data.
     * @return The updated SourceDTO.
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/{sourceId}")
    public SourceDTO updateSource(
            @PathVariable Long sourceId,
            @Valid @RequestBody SourceDTO sourceDTO) {
        // Ensure the ID in the path matches the ID in the DTO, if provided in DTO
        if (sourceDTO.getId() != null && !sourceId.equals(sourceDTO.getId())) {
            // You might want to throw an IllegalArgumentException or handle this more gracefully
            // For now, let's proceed with the path variable ID as the definitive one.
            sourceDTO.setId(sourceId);
        }
        return sourceService.updateSource(sourceId, sourceDTO);
    }

    /**
     * Deletes a source by ID. Accessible only by ADMIN role.
     * @param sourceId The ID of the source to delete.
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{sourceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Returns 204 No Content on successful deletion
    public void deleteSource(@PathVariable Long sourceId) {
        sourceService.removeSource(sourceId);
    }
}
