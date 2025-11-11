package cz.rodro.dto;

import cz.rodro.constant.ConfessionType;
import cz.rodro.constant.SourceType;

/**
 * Projection interface for retrieving lightweight source data.
 *
 * Used when querying large collections of sources to avoid fetching
 * full entity graphs. Provides only essential fields, including
 * temporal data and location reference.
 */
public interface SourceListProjection {

    /** Unique identifier of the source. */
    Long getId();

    /** Title of the source. */
    String getTitle();

    /** Reference or citation of the source (e.g., bibliographic reference). */
    String getReference();

    /** Description providing additional context or notes about the source. */
    String getDescription();

    /** URL linking to the source, if available. */
    String getUrl();

    /** Type of the source (e.g., BOOK, ARTICLE, WEBSITE). */
    SourceType getType();

    ConfessionType getConfession();

    // --- NEW TEMPORAL GETTERS ---
    /** Year the source was created or published. */
    Integer getCreationYear();

    /** Temporal start year of the data contained within the source. */
    Integer getStartYear();

    /** Temporal end year of the data contained within the source. */
    Integer getEndYear();
    // ----------------------------

    /** Identifier of the associated location (lightweight reference). */
    Long getLocationId();

    /** Human-readable name of the associated location. */
    String getLocationName();

    default Integer getEffectiveYear() {
        if (getCreationYear() != null) {
            return getCreationYear();
        }
        if (getStartYear() != null) {
            return getStartYear();
        }
        // Use endYear as a final fallback, or 0 if all are null.
        return getEndYear();
    }

}
