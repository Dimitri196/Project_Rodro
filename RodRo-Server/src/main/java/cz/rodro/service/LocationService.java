package cz.rodro.service;


import cz.rodro.dto.LocationDTO;
import cz.rodro.entity.LocationEntity;

import java.util.List;

public interface LocationService {

    List<LocationDTO> getAll();

    LocationDTO getLocation(long locationId);

    LocationDTO addLocation(LocationDTO locationDTO);

    LocationDTO removeLocation(long locationId);

    LocationDTO updateLocation(Long locationId, LocationDTO locationDTO);

    LocationEntity fetchLocationById(Long id, String type);

}
