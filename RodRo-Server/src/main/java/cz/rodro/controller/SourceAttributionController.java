package cz.rodro.controller;

import cz.rodro.dto.SourceAttributionDTO;
import cz.rodro.service.SourceAttributionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/sourceAttributions")
@RequiredArgsConstructor
public class SourceAttributionController {

    private final SourceAttributionService sourceAttributionService;

    /**
     * Get all source attributions linked to a person directly (e.g. birth, death)
     * or filtered by event type (e.g. BIRTH, DEATH).
     */
    @GetMapping("/person/{personId}")
    public List<SourceAttributionDTO> getPersonSources(
            @PathVariable Long personId,
            @RequestParam(required = false) String eventType
    ) {
        if (eventType != null && !eventType.isBlank()) {
            return sourceAttributionService.getByPersonIdAndEventType(personId, eventType);
        }
        return sourceAttributionService.getByPersonId(personId);
    }

    /**
     * Get all sources connected to the person, including indirect links
     * (e.g. through Occupation, Family, or Military Service).
     */
    @GetMapping("/person/{personId}/all")
    public List<SourceAttributionDTO> getPersonSourcesAll(@PathVariable Long personId) {
        return sourceAttributionService.getByPersonIdAllTargets(personId);
    }

    /**
     * Create a new source attribution.
     */
    @PostMapping
    public ResponseEntity<SourceAttributionDTO> createAttribution(@RequestBody @Valid SourceAttributionDTO dto) {
        // Basic validation: must have sourceId and type
        if (dto.getSourceId() == null || dto.getType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sourceId and type are required");
        }

        SourceAttributionDTO created = sourceAttributionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Delete a source attribution.
     */
    @DeleteMapping("/{id}")
    public void deleteAttribution(@PathVariable Long id) {
        sourceAttributionService.delete(id);
    }
}
