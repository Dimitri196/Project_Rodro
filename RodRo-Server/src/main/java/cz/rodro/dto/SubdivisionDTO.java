package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubdivisionDTO {

    @JsonProperty("_id")
    private Long id;

    private String subdivisionName;

    private DistrictDTO district;
    private String districtName;

    private LocationDTO administrativeCenter;
    private String administrativeCenterName;

    private String subdivisionEstablishmentYear;
    private String subdivisionCancellationYear;

    private List<LocationDTO> locations;
}