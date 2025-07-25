package cz.rodro.controller;

import cz.rodro.dto.SourceDTO;
import cz.rodro.service.SourceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sources")
public class SourceController {

    @Autowired
    private SourceService sourceService;

    // Create a new source
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SourceDTO addSource(@Valid @RequestBody SourceDTO sourceDTO) {
        return sourceService.addSource(sourceDTO);
    }

    // Get all sources
    @GetMapping
    public List<SourceDTO> getAllSources() {
        return sourceService.getAllSources();
    }

    // Get a specific source by ID
    @GetMapping("/{sourceId}")
    public SourceDTO getSource(@PathVariable Long sourceId) {
        return sourceService.getSource(sourceId);
    }

    // Update an existing source
    @PutMapping("/{sourceId}")
    public SourceDTO updateSource(
            @PathVariable Long sourceId,
            @Valid @RequestBody SourceDTO sourceDTO) {
        return sourceService.updateSource(sourceId, sourceDTO);
    }

    // Delete a source by ID
    @DeleteMapping("/{sourceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSource(@PathVariable Long sourceId) {
        sourceService.deleteSource(sourceId);
    }
}
