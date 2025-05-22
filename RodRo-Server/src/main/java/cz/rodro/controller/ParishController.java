package cz.rodro.controller;

import cz.rodro.dto.ParishDTO;
import cz.rodro.service.ParishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParishController {

    @Autowired
    private ParishService parishService;

    @PostMapping("/parishes")
    public ParishDTO addParish(@RequestBody ParishDTO parishDTO) {
        return parishService.addParish(parishDTO);
    }

    @GetMapping("/parishes/{parishId}")
    public ParishDTO getParish(@PathVariable long parishId) {
        return parishService.getParish(parishId);
    }

    @PutMapping("/parishes/{parishId}")
    public ParishDTO updateParish(@PathVariable long parishId, @RequestBody ParishDTO parishDTO) {
        // Validate input
        if (parishDTO.getId() == null || !parishDTO.getId().equals(parishId)) {
            throw new IllegalArgumentException("ID mismatch or missing ID");
        }

        return parishService.updateParish(parishId, parishDTO);
    }

    @DeleteMapping("/parishes/{parishId}")
    public String removeParish(@PathVariable long parishId) {
        parishService.removeParish(parishId);
        return "Parish deleted successfully.";
    }

    @GetMapping("/parishes")
    public List<ParishDTO> getAllParishes() {
        return parishService.getAllParishes();
    }
}
