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
 * Data Transfer Object (DTO) for transferring source-related information
 * between different layers of the application (e.g., backend services and frontend clients).
 *
 * <p>This class encapsulates metadata about a source, including its title,
 * reference, description, URL, type, and related location details.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceDTO {

    /**
     * Unique identifier of the source.
     * <p>Serialized as "_id" in JSON responses for compatibility with frontend expectations.</p>
     */
    @JsonProperty("_id")
    private Long id;

    /**
     * Title of the source.
     * <p>Required field with a maximum length of 255 characters.</p>
     */
    @NotBlank(message = "Source title cannot be empty")
    @Size(max = 255, message = "Source title cannot exceed 255 characters")
    private String title;

    /**
     * Reference or citation of the source (e.g., bibliographic reference).
     * <p>Optional field with a maximum length of 255 characters.</p>
     */
    @Size(max = 255, message = "Source reference cannot exceed 255 characters")
    private String reference;

    /**
     * Description providing additional context or notes about the source.
     * <p>Optional field with a maximum length of 1000 characters.</p>
     */
    @Size(max = 1000, message = "Source description cannot exceed 1000 characters")
    private String description;

    /**
     * URL linking to the source, if available.
     * <p>Optional field with a maximum length of 500 characters.</p>
     */
    @Size(max = 500, message = "Source URL cannot exceed 500 characters")
    private String url;

    /**
     * Type of the source (e.g., BOOK, ARTICLE, WEBSITE).
     * <p>This field is mandatory and must not be null.</p>
     */
    @NotNull(message = "Source type cannot be null")
    private SourceType type;

    /**
     * Identifier of the location associated with the source, if applicable.
     * <p>Used to reference a related location entity.</p>
     */
    private Long locationId;

    /**
     * Human-readable name of the location associated with the source.
     * <p>Typically displayed in the UI for clarity.</p>
     */
    private String locationName;
}
