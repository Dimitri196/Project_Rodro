package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationHistoryDTO {

    @JsonProperty("_id")
    private Long id;

    private String countryName;
    private String provinceName;
    private String districtName;
    private String subdivisionName;
    private String locationName;

    private LocalDate startDate;
    private LocalDate endDate;

    private String notes;

    private Long countryId;
    private Long provinceId;
    private Long districtId;
    private Long subdivisionId;
    private Long locationId;
}