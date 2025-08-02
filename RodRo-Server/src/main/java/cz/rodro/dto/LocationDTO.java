package cz.rodro.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.SettlementType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {

    @JsonProperty("_id") // Maps the JSON field "_id" to the Java field "id"
    private Long id;

    @NotBlank(message = "Location name cannot be empty")
    @Size(max = 255, message = "Location name cannot exceed 255 characters")
    private String locationName;

    @Min(value = 0, message = "Establishment year cannot be negative")
    @Max(value = 9999, message = "Establishment year cannot exceed 9999")
    private Integer establishmentYear; // Changed to Integer for just the year

    @Min(value = -90, message = "Latitude must be between -90 and 90")
    @Max(value = 90, message = "Latitude must be between -90 and 90")
    private Double gpsLatitude; // Changed to Double for numerical GPS

    @Min(value = -180, message = "Longitude must be between -180 and 180")
    @Max(value = 180, message = "Longitude must be between -180 and 180")
    private Double gpsLongitude; // Changed to Double for numerical GPS

    @NotNull(message = "Settlement type cannot be null")
    private SettlementType settlementType;

    // Assuming LocationHistoryDTO and SourceDTO exist and are correctly defined
    private List<LocationHistoryDTO> locationHistories; // Renamed to match entity's plural form

    private List<SourceDTO> sources; // Renamed to match entity's plural form and standard naming

    private List<ParishDTO> parishes; // Renamed to match entity's plural form and standard naming
}
