package cz.rodro.controller;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.service.MilitaryStructureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MilitaryStructureController {

    @Autowired
    private MilitaryStructureService militaryStructureService;

    @GetMapping("/militaryStructures")
    public List<MilitaryStructureDTO> getAll() {
        return militaryStructureService.getAll();
    }

    @GetMapping("/militaryStructures/{id}")
    public MilitaryStructureDTO getById(@PathVariable Long id) {
        return militaryStructureService.getMilitaryStructure(id);
    }

    @PostMapping("/militaryStructures")
    @ResponseStatus(HttpStatus.CREATED)
    public MilitaryStructureDTO add(@Valid @RequestBody MilitaryStructureDTO dto) {
        return militaryStructureService.addMilitaryStructure(dto);
    }

    @PutMapping("/militaryStructures/{id}")
    public MilitaryStructureDTO update(@PathVariable Long id, @Valid @RequestBody MilitaryStructureDTO dto) {
        return militaryStructureService.updateMilitaryStructure(id, dto);
    }

    @DeleteMapping("/militaryStructures/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        militaryStructureService.removeMilitaryStructure(id);
    }

    @GetMapping("/militaryStructures/{id}/ranks")
    public List<MilitaryRankDTO> getRanksForStructure(@PathVariable Long id) {
        return militaryStructureService.getRanksForStructure(id);
    }
}