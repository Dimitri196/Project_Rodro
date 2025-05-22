package cz.rodro.controller;

import cz.rodro.dto.PersonSourceEvidenceDTO;
import cz.rodro.service.PersonSourceEvidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/personSourceEvidences")
public class PersonSourceEvidenceController {

    @Autowired
    private PersonSourceEvidenceService personSourceEvidenceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonSourceEvidenceDTO add(@RequestBody PersonSourceEvidenceDTO dto) {
        return personSourceEvidenceService.add(dto);
    }

    @GetMapping("/{id}")
    public PersonSourceEvidenceDTO get(@PathVariable Long id) {
        return personSourceEvidenceService.get(id);
    }

    @GetMapping
    public List<PersonSourceEvidenceDTO> getAll() {
        return personSourceEvidenceService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        personSourceEvidenceService.remove(id);
    }
}