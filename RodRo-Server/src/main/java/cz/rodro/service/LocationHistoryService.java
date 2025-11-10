package cz.rodro.service;

import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.LocationHistoryDTO;
import java.util.List;

public interface LocationHistoryService {

    List<LocationHistoryDTO> getHistoryForLocation(Long locationId);

    LocationHistoryDTO addLocationHistory(Long locationId, LocationHistoryDTO locationHistoryDTO);

    LocationHistoryDTO updateLocationHistory(Long historyId, LocationHistoryDTO locationHistoryDTO);

    void deleteLocationHistory(Long historyId);

    List<LocationHistoryDTO> getHistoryForDistrict(Long districtId);

    List<LocationHistoryDTO> getHistoryForSubdivision(Long subdivisionId);

    List<LocationDTO> getLocationsBySubdivisionId(Long subdivisionId);

    List<LocationDTO> getLocationsByDistrictId(Long districtId);
}
