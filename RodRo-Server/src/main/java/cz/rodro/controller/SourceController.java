package cz.rodro.controller;

import cz.rodro.dto.SourceDTO;
import cz.rodro.dto.SourceListDTO;
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
import java.util.Map;

/**
 * REST controller for managing sources.
 * Supports CRUD operations and paginated, searchable list views.
 */
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
     *
     * @param sourceDTO The DTO containing source data.
     * @return ResponseEntity with the created SourceDTO and HTTP status 201 Created.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<SourceDTO> addSource(@Valid @RequestBody SourceDTO sourceDTO) {
        SourceDTO created = sourceService.addSource(sourceDTO);
        return ResponseEntity
                .created(URI.create("/api/sources/" + created.getId()))
                .body(created);
    }

    /**
     * Retrieves a paginated, filtered, and sorted list of sources.
     * Uses the request parameter map to capture dynamic column filters.
     *
     * @param params All query parameters, including pagination, sorting, and filters (e.g., filter_title).
     * @return A Page of {@link SourceListDTO}.
     */
    @GetMapping
    public ResponseEntity<Page<SourceListDTO>> getAllSources(
            @RequestParam Map<String, String> params
    ) {
        int page = Integer.parseInt(params.getOrDefault("page", "0"));
        int size = Integer.parseInt(params.getOrDefault("size", "10"));
        String sortBy = params.getOrDefault("sort", "title,asc").split(",")[0];
        String sortOrder = params.getOrDefault("sort", "title,asc").split(",")[1];
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder.toUpperCase()), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // --- 2. Call Service with the full parameter map ---
        Page<SourceListDTO> sources = sourceService.getAllSourcesAsDTO(params, pageable);
        return ResponseEntity.ok(sources);
    }

    /**
     * Retrieves a specific source by ID.
     *
     * @param sourceId The ID of the source.
     * @return The {@link SourceDTO}.
     */
    @GetMapping("/{sourceId}")
    public ResponseEntity<SourceDTO> getSource(@PathVariable Long sourceId) {
        SourceDTO dto = sourceService.getSource(sourceId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Updates an existing source. Accessible only by ADMIN role.
     *
     * @param sourceId  The ID of the source to update.
     * @param sourceDTO The DTO containing updated source data.
     * @return The updated {@link SourceDTO}.
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/{sourceId}")
    public ResponseEntity<SourceDTO> updateSource(
            @PathVariable Long sourceId,
            @Valid @RequestBody SourceDTO sourceDTO
    ) {
        sourceDTO.setId(sourceId);
        SourceDTO updated = sourceService.updateSource(sourceId, sourceDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a source by ID. Accessible only by ADMIN role.
     *
     * @param sourceId The ID of the source to delete.
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{sourceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSource(@PathVariable Long sourceId) {
        sourceService.removeSource(sourceId);
    }
}
