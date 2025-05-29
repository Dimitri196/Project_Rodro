package cz.rodro.controller;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.service.MilitaryOrganizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MilitaryOrganizationController {

    @Autowired
    private MilitaryOrganizationService militaryOrganizationService;

    @GetMapping("/militaryOrganizations")
    public List<MilitaryOrganizationDTO> getAll() {
        return militaryOrganizationService.getAll();
    }

    @GetMapping("/militaryOrganizations/{id}")
    public MilitaryOrganizationDTO getMilitaryOrganization(@PathVariable Long id) {
        return militaryOrganizationService.getMilitaryOrganization(id);
    }

    @PostMapping("/militaryOrganizations")
    @ResponseStatus(HttpStatus.CREATED)
    public MilitaryOrganizationDTO add(@Valid @RequestBody MilitaryOrganizationDTO dto) {
        return militaryOrganizationService.addMilitaryOrganization(dto);
    }

    @PutMapping("/militaryOrganizations/{id}")
    public MilitaryOrganizationDTO update(@PathVariable Long id, @Valid @RequestBody MilitaryOrganizationDTO dto) {
        return militaryOrganizationService.updateMilitaryOrganization(id, dto);
    }

    @DeleteMapping("/militaryOrganizations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        militaryOrganizationService.removeMilitaryOrganization(id);
    }
}