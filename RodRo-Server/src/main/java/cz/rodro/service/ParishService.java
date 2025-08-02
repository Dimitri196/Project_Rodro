package cz.rodro.service;

import cz.rodro.dto.ParishDTO;
import cz.rodro.dto.ParishLocationDTO;

import java.util.List;

public interface ParishService {

    ParishDTO addParish(ParishDTO parishDTO);
    ParishDTO getParish(long parishId);
    ParishDTO updateParish(Long parishId, ParishDTO parishDTO);
    void removeParish(long parishId);
    List<ParishDTO> getAllParishes();
    // Add this new method signature
    List<ParishLocationDTO> getParishLocations(long parishId);
    public List<ParishLocationDTO> getParishesByLocationId(long locationId);

}
