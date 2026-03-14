package core;

/**
 * Custom exception thrown when a framework operation fails
 * (e.g., element interaction, page load timeout, configuration error).
 * Extends RuntimeException for cleaner test code without checked exception boilerplate.
 */
public class FrameworkException extends RuntimeException {

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
