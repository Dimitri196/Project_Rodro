package cz.rodro.service;

import cz.rodro.dto.PersonOccupationDTO;

import java.util.List;

public interface PersonOccupationService {

    PersonOccupationDTO createLink(PersonOccupationDTO dto);
    List<PersonOccupationDTO> getAllLinks();
    List<PersonOccupationDTO> getByPersonId(Long personId);
    void deleteLink(Long id);

}