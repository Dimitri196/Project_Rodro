package cz.rodro.service;

import cz.rodro.dto.ParishLocationDTO;
import java.util.List;

public interface ParishLocationService {

    List<ParishLocationDTO> getParishesByLocationId(Long locationId);
    ParishLocationDTO addParishToLocation(Long locationId, ParishLocationDTO parishLocationDTO);
    void removeParishFromLocation(Long locationId, Long parishId);

}
