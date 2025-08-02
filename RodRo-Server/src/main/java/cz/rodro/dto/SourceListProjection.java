package cz.rodro.dto;

import cz.rodro.constant.SourceType;

/**
 * Projection interface for a lightweight view of SourceEntity.
 * Useful for lists where only basic information is needed,
 * avoiding the N+1 query problem for associated collections.
 */
public interface SourceListProjection {
    Long getId();
    String getSourceTitle();
    String getSourceReference();
    String getSourceDescription();
    String getSourceUrl();
    SourceType getSourceType();
    // For the associated Location, we only need its ID and name to break recursion
    Long getSourceLocationId();
    String getSourceLocationName();
}
