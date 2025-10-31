package cz.rodro.exception;

/**
 * Custom exception to be thrown when an authenticated user attempts to access
 * a resource or perform an action they are not authorized for (e.g.,
 * editing another user's article).
 */
public class AccessDeniedException extends RuntimeException {

    // Standard constructor that accepts a message
    public AccessDeniedException(String message) {
        super(message);
    }

    // Optional: Constructor that accepts a message and the underlying cause
    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
