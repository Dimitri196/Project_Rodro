package cz.rodro.dto;

import cz.rodro.constant.SourceType;

/**
 * Projection interface for retrieving lightweight source data.
 *
 * <p>Used when querying large collections of sources to avoid fetching
 * full entity graphs. Provides only essential fields, including
 * location ID and name, to prevent recursion and unnecessary joins.</p>
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

    /** Identifier of the associated location (lightweight reference). */
    Long getLocationId();

    /** Human-readable name of the associated location. */
    String getLocationName();
}
