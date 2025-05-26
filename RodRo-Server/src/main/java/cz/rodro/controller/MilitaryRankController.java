package cz.rodro.controller;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.service.MilitaryRankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/militaryRanks")
public class MilitaryRankController {

    @Autowired
    private MilitaryRankService militaryRankService;

    @GetMapping
    public List<MilitaryRankDTO> getAll() {
        return militaryRankService.getAll();
    }

    @GetMapping("/{id}")
    public MilitaryRankDTO getById(@PathVariable Long id) {
        return militaryRankService.getMilitaryRank(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MilitaryRankDTO add(@Valid @RequestBody MilitaryRankDTO dto) {
        return militaryRankService.addMilitaryRank(dto);
    }

    @PutMapping("/{id}")
    public MilitaryRankDTO update(@PathVariable Long id, @Valid @RequestBody MilitaryRankDTO dto) {
        return militaryRankService.updateMilitaryRank(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        militaryRankService.removeMilitaryRank(id);
    }
}