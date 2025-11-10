package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for the temporal link between a Country and a Continent.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryContinentHistoryDTO {

    @JsonProperty("_id")
    private Long id;

    @NotNull(message = "Country ID is required for history.")
    private Long countryId;

    @NotNull(message = "Continent ID is required for history.")
    private Long continentId;

    /** The human-readable name of the continent (e.g., "Europe"). */
    private String continentName;

    @NotNull(message = "Start year is required.")
    @Size(min = 1, max = 15, message = "Start year must be a valid historical notation.")
    private String startYear;

    @Size(max = 15, message = "End year must be a valid historical notation.")
    private String endYear;
}
