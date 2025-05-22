package cz.rodro.service;

import cz.rodro.dto.CemeteryDTO;

import java.util.List;

public interface CemeteryService {

    List<CemeteryDTO> getAllCemeteries();

    CemeteryDTO getCemeteryById(long cemeteryId);

    CemeteryDTO addCemetery(CemeteryDTO cemeteryDTO);

    CemeteryDTO updateCemetery(Long cemeteryId, CemeteryDTO cemeteryDTO);

    void deleteCemetery(Long cemeteryId);

    List<CemeteryDTO> getCemeteriesByLocationId(Long locationId);
}