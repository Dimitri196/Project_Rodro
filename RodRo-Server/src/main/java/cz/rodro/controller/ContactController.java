package cz.rodro.controller;

import cz.rodro.dto.ContactMessageDTO;
import cz.rodro.service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactController {

    private final ContactMessageService contactService;

    public ContactController(ContactMessageService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactMessageDTO submitContact(@Valid @RequestBody ContactMessageDTO dto) {
        return contactService.save(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/contact")
    public List<ContactMessageDTO> getAllMessages() {
        return contactService.getAll();
    }
}
