package cz.rodro.exception;

public class DuplicateEmailException extends RuntimeException {

    // Default constructor
    public DuplicateEmailException() {
        super("Email already exists.");
    }

    // Constructor that accepts a custom message
    public DuplicateEmailException(String message) {
        super(message);
    }
}