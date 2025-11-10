package cz.rodro.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Custom exception to indicate that a specific resource (entity) identified by
 * a field (e.g., ID, name) was not found in the database.
 * * @implNote Uses Lombok's @Getter for field access and @RequiredArgsConstructor
 * to generate the field-specific constructor.
 */
@Getter
@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    /**
     * Custom Constructor for a generic 'Not Found' scenario, keeping the fields null.
     * This is manually implemented because it requires specific logic (setting fields to null
     * and calling the superclass constructor with a generic message).
     */
    public ResourceNotFoundException(String message) {
        super(message);
        this.resourceName = null;
        this.fieldName = null;
        this.fieldValue = null;
    }

}
