package cz.rodro.exception;

/**
 * Custom exception to indicate that an entity was not found.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}