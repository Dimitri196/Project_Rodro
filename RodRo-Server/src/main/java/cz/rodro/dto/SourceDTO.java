package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.SourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Source.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceDTO {

    @JsonProperty("_id")
    private Long id;

    @NotBlank(message = "Source title cannot be empty")
    @Size(max = 255, message = "Source title cannot exceed 255 characters")
    private String sourceTitle;

    @Size(max = 255, message = "Source reference cannot exceed 255 characters")
    private String sourceReference;

    @Size(max = 1000, message = "Source description cannot exceed 1000 characters")
    private String sourceDescription;

    @Size(max = 500, message = "Source URL cannot exceed 500 characters")
    private String sourceUrl;

    @NotNull(message = "Source type cannot be null")
    private SourceType sourceType;

    // Changed from LocationDTO to just ID and Name to break recursion
    private Long sourceLocationId;
    private String sourceLocationName; // For display purposes
}
