package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.ConfessionType;
import cz.rodro.constant.SourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Data Transfer Object (DTO) for transferring source-related information.
 * Encapsulates all fields from SourceEntity, including flexible metadata and citation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceDTO {

    @JsonProperty("_id")
    private Long id;

    @NotBlank(message = "Source title cannot be empty")
    @Size(max = 255, message = "Source title cannot exceed 255 characters")
    private String title;

    @Size(max = 255, message = "Source reference cannot exceed 255 characters")
    private String reference;

    /**
     * Description providing additional context or notes about the source.
     * Increased size limit to accommodate scholarly notes (since the Entity uses @Lob).
     */
    @Size(max = 5000, message = "Source description cannot exceed 5000 characters") // Adjusted size
    private String description;

    /**
     * URL linking to the source, if available.
     */
    @Size(max = 500, message = "Source URL cannot exceed 500 characters")
    private String url;

    @NotNull(message = "Source type cannot be null")
    private SourceType type;

    @NotNull(message = "Confession cannot be null")
    private ConfessionType confession;

    // --- Temporal Fields ---
    private Integer creationYear;
    private Integer startYear;
    private Integer endYear;

    // --- Hybrid Fields ---

    /**
     * Flexible, type-specific metadata stored as JSON/JSONB.
     */
    private Map<String, Object> metadata;

    /**
     * A standardized citation string (e.g., Chicago, MLA style).
     */
    private String citationString;

    // --- Location Fields ---

    private Long locationId;

    private String locationName;
}
