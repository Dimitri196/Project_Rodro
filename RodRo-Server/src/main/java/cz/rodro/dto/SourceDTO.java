package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.SourceType;
import jakarta.validation.constraints.NotBlank;
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

    /**
     * A descriptive and human-readable title of the archival source.
     * Should include type, year(s), denomination/institution, and locality.
     * Example: "Metrical Books, 1859, Roman Catholic Church, Parish of Łohiszyn"
     *
     * Purpose: Helps users quickly identify the content and origin of the record.
     */
    @NotBlank
    private String sourceTitle;

    /**
     * An optional extended description providing additional context about the source.
     * Can include information such as:
     * - language of the record (e.g., Polish, Russian, Latin)
     * - condition of the documents
     * - coverage (e.g., baptisms only, or full lifecycle)
     * - known gaps or historical notes (e.g., war damage, duplicate entries)
     *
     * Useful for researchers to evaluate the reliability and scope of the source.
     */
    private String sourceDescription;

    /**
     * Formal archival reference for locating the physical or digital source.
     * Should follow the naming conventions of the holding archive.
     *
     * Format example (Eastern European archives):
     * "National Historical Archives of Belarus, Fond 937, Opis 4, Delo 196"
     *
     * Format example (US standard):
     * "NARA, Record Group 233, Series 12, File 471"
     *
     * Purpose: Enables citation, verification, and retrieval.
     */
    private String sourceReference;

    /**
     * Enum value defining the category or classification of the source.
     * Example values:
     * - CHURCH_RECORD
     * - CENSUS
     * - LAND_REGISTRY
     * - IMMIGRATION
     * - MILITARY
     *
     * Used for filtering, UI labeling, and record classification.
     */
    private SourceType sourceType;

    /**
     * Optional direct link to a digital archive or digitized version of the source.
     * Can point to:
     * - a scanned collection on an archive website (e.g., https://niab.by)
     * - a published database or online repository
     * - a PDF or static file link if publicly accessible
     *
     * Should be a persistent or stable URL when possible.
     */
    private String sourceUrl;

    private LocationDTO sourceLocation; // Correct, use LocationDTO to transfer location details to frontend
}
