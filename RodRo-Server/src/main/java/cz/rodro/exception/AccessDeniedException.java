package cz.rodro.exception;

/**
 * Custom exception to be thrown when an authenticated user attempts to access
 * a resource or perform an action they are not authorized for (e.g.,
 * editing another user's article).
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
