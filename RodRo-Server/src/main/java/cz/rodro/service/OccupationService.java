package cz.rodro.service;

import cz.rodro.dto.OccupationDTO;

import java.util.List;

public interface OccupationService {

    OccupationDTO createOccupation(OccupationDTO occupationDTO);
    OccupationDTO getOccupationById(Long occupationId);
    List<OccupationDTO> getAllOccupations();
    void deleteOccupation(Long occupationId);

}