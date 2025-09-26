package cz.rodro.service;

import cz.rodro.dto.InstitutionDTO;

import java.util.List;

public interface InstitutionService {

    InstitutionDTO getInstitutionById(Long id);

    List<InstitutionDTO> getAllInstitutions();

    InstitutionDTO createInstitution(InstitutionDTO institutionDTO);

    InstitutionDTO updateInstitution(Long id, InstitutionDTO institutionDTO);

    void deleteInstitution(Long id);
}
