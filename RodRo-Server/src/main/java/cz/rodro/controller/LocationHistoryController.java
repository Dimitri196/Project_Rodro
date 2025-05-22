package cz.rodro.controller;

import cz.rodro.dto.LocationHistoryDTO;
import cz.rodro.service.LocationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations/{locationId}/history")
public class LocationHistoryController {

    @Autowired
    private LocationHistoryService locationHistoryService;

    @GetMapping
    public List<LocationHistoryDTO> getHistory(@PathVariable Long locationId) {
        return locationHistoryService.getHistoryForLocation(locationId);
    }

    @PostMapping
    public LocationHistoryDTO addHistory(@PathVariable Long locationId, @RequestBody LocationHistoryDTO historyDTO) {
        return locationHistoryService.addLocationHistory(locationId, historyDTO);
    }

    @PutMapping("/{historyId}")
    public LocationHistoryDTO updateHistory(@PathVariable Long historyId, @RequestBody LocationHistoryDTO historyDTO) {
        return locationHistoryService.updateLocationHistory(historyId, historyDTO);
    }

    @DeleteMapping("/{historyId}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long historyId) {
        locationHistoryService.deleteLocationHistory(historyId);
        return ResponseEntity.noContent().build();
    }
}