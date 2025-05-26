package cz.rodro.controller;

import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.service.PersonMilitaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/personMilitaryServices")
public class PersonMilitaryServiceController {

    @Autowired
    private PersonMilitaryService personMilitaryService;

    @GetMapping
    public List<PersonMilitaryServiceDTO> getAll() {
        return personMilitaryService.getAll();
    }

    @GetMapping("/{id}")
    public PersonMilitaryServiceDTO getById(@PathVariable Long id) {
        return personMilitaryService.getPersonMilitaryService(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonMilitaryServiceDTO add(@Valid @RequestBody PersonMilitaryServiceDTO dto) {
        return personMilitaryService.addPersonMilitaryService(dto);
    }

    @PutMapping("/{id}")
    public PersonMilitaryServiceDTO update(@PathVariable Long id, @Valid @RequestBody PersonMilitaryServiceDTO dto) {
        return personMilitaryService.updatePersonMilitaryService(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        personMilitaryService.removePersonMilitaryService(id);
    }

    @GetMapping(params = "personId")
    public List<PersonMilitaryServiceDTO> getByPersonId(@RequestParam Long personId) {
        return personMilitaryService.getByPersonId(personId);
    }
}