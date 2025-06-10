package cz.rodro.controller;

import cz.rodro.dto.DTreeNodeDTO;
import cz.rodro.service.DTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FamilyTreeController {

    @Autowired
    private DTreeService dTreeService;

    @GetMapping("/family-tree/{personId}")
    public List<DTreeNodeDTO> getFamilyTreeForPerson(@PathVariable Long personId) {
        return dTreeService.generateDTreeForPerson(personId);
    }
}
