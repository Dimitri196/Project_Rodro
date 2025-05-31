package cz.rodro.controller;

import cz.rodro.dto.MilitaryArmyBranchDTO;
import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.service.MilitaryArmyBranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MilitaryArmyBranchController {

    @Autowired
    private MilitaryArmyBranchService militaryArmyBranchService;

    @GetMapping("/militaryArmyBranches")
    public List<MilitaryArmyBranchDTO> getAll() {
        return militaryArmyBranchService.findAll();
    }

    @GetMapping("/militaryArmyBranches/{id}")
    public MilitaryArmyBranchDTO getById(@PathVariable Long id) {
        return militaryArmyBranchService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MilitaryArmyBranchDTO add(@Valid @RequestBody MilitaryArmyBranchDTO dto) {
        return militaryArmyBranchService.create(dto);
    }

    @PutMapping("/militaryArmyBranches/{id}")
    public MilitaryArmyBranchDTO update(@PathVariable Long id, @Valid @RequestBody MilitaryArmyBranchDTO dto) {
        return militaryArmyBranchService.update(id, dto);
    }

    @DeleteMapping("/militaryArmyBranches/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        militaryArmyBranchService.delete(id);
    }

    @GetMapping("/militaryArmyBranches/{id}/ranks")
    public List<MilitaryRankDTO> getRanksByBranchId(@PathVariable Long id) {
        return militaryArmyBranchService.getRanksByBranchId(id);
    }
}

