package cz.rodro.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.SettlementType;
import cz.rodro.validation.PastOrPresentYear;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a Location entity.
 * <p>
 * A Location represents a geographical or administrative place
 * (e.g., city, village, parish) with additional metadata such as
 * establishment year, GPS coordinates, settlement type, and related
 * historical and source information.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    /**
     * Unique identifier of the location.
     */
    @JsonProperty("_id")
    private Long id;

    /**
     * Name of the location (e.g., "Prague", "Brno").
     */
    @NotBlank(message = "Location name cannot be empty")
    @Size(max = 255, message = "Location name cannot exceed 255 characters")
    private String locationName;

    /**
     * Year of establishment of the location.
     */
    @Min(value = 0, message = "Establishment year cannot be negative")
    @PastOrPresentYear
    private Integer establishmentYear;

    /**
     * Latitude of the location in decimal degrees (-90.0 to 90.0).
     */
    @DecimalMin(value = "-90.0", inclusive = true, message = "Latitude must be >= -90.0")
    @DecimalMax(value = "90.0", inclusive = true, message = "Latitude must be <= 90.0")
    private Double gpsLatitude;

    /**
     * Longitude of the location in decimal degrees (-180.0 to 180.0).
     */
    @DecimalMin(value = "-180.0", inclusive = true, message = "Longitude must be >= -180.0")
    @DecimalMax(value = "180.0", inclusive = true, message = "Longitude must be <= 180.0")
    private Double gpsLongitude;

    /**
     * Type of settlement (e.g., VILLAGE, CITY, TOWN).
     */
    @NotNull(message = "Settlement type cannot be null")
    private SettlementType settlementType;

    /**
     * Historical changes related to this location (renaming, administrative changes).
     */
    private List<LocationHistoryDTO> locationHistories;

    /**
     * Sources documenting this location.
     */
    //private List<SourceDTO> sources;

    /**
     * Sources documenting this location.
     * ✅ REFACTOR: Using SourceSummaryDTO for lightweight transfer.
     */
    private List<SourceSummaryDTO> sources;

    /**
     * Parishes associated with this location.
     */
    private List<ParishDTO> parishes;

    /**
     * URL of an image representing the location.
     */
    private String locationImageUrl;
}
