package cz.rodro.controller;

import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.SubdivisionDTO;
import cz.rodro.service.LocationHistoryService;
import cz.rodro.service.SubdivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubdivisionController {

    @Autowired
    private SubdivisionService subdivisionService;

    @Autowired
    private LocationHistoryService locationHistoryService;

    @PostMapping("/subdivisions")
    public SubdivisionDTO createSubdivision(@RequestBody SubdivisionDTO subdivisionDTO) {
        return subdivisionService.create(subdivisionDTO);
    }

    @GetMapping("/subdivisions/{subdivisionId}")
    public SubdivisionDTO getSubdivision(@PathVariable long subdivisionId) {
        return subdivisionService.getById(subdivisionId);
    }

    @PutMapping({"/subdivisions/{subdivisionId}", "/subdivisions/{subdivisionId}/"})
    public SubdivisionDTO updateSubdivision(@PathVariable long subdivisionId, @RequestBody SubdivisionDTO subdivisionDTO) {
        return subdivisionService.update(subdivisionId, subdivisionDTO);
    }

    @DeleteMapping({"/subdivisions/{subdivisionId}", "/subdivisions/{subdivisionId}/"})
    public void deleteSubdivision(@PathVariable long subdivisionId) {
        subdivisionService.delete(subdivisionId);
    }

    @GetMapping("/subdivisions")
    public List<SubdivisionDTO> getAllSubdivisions() {
        return subdivisionService.getAll();
    }

    @GetMapping("/{id}/locations")
    public ResponseEntity<List<LocationDTO>> getLocationsForSubdivision(@PathVariable Long id) {
        List<LocationDTO> locations = locationHistoryService.getLocationsBySubdivisionId(id);
        return ResponseEntity.ok(locations);
    }
}
