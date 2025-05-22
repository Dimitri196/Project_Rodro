package cz.rodro.controller;

import cz.rodro.dto.LocationHistoryDTO;
import cz.rodro.service.LocationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DistrictController {

    @Autowired
    private LocationHistoryService locationHistoryService;

    @GetMapping("/districts/{districtId}/locations")
    public ResponseEntity<List<LocationHistoryDTO>> getLocationsForDistrict(@PathVariable Long districtId) {
        List<LocationHistoryDTO> locationHistories = locationHistoryService.getHistoryForDistrict(districtId);
        return ResponseEntity.ok(locationHistories);
    }
}