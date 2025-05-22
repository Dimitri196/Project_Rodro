package cz.rodro.service;

import cz.rodro.dto.PersonSourceEvidenceDTO;

import java.util.List;

public interface PersonSourceEvidenceService {
    PersonSourceEvidenceDTO add(PersonSourceEvidenceDTO dto);
    void remove(Long id);
    PersonSourceEvidenceDTO get(Long id);
    List<PersonSourceEvidenceDTO> getAll();
}
