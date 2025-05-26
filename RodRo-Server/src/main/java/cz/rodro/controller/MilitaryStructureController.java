package cz.rodro.controller;

import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.service.MilitaryStructureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/militaryStructures")
public class MilitaryStructureController {

    @Autowired
    private MilitaryStructureService militaryStructureService;

    @GetMapping
    public List<MilitaryStructureDTO> getAll() {
        return militaryStructureService.getAll();
    }

    @GetMapping("/{id}")
    public MilitaryStructureDTO getById(@PathVariable Long id) {
        return militaryStructureService.getMilitaryStructure(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MilitaryStructureDTO add(@Valid @RequestBody MilitaryStructureDTO dto) {
        return militaryStructureService.addMilitaryStructure(dto);
    }

    @PutMapping("/{id}")
    public MilitaryStructureDTO update(@PathVariable Long id, @Valid @RequestBody MilitaryStructureDTO dto) {
        return militaryStructureService.updateMilitaryStructure(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        militaryStructureService.removeMilitaryStructure(id);
    }
}