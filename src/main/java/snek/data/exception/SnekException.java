package snek.data.exception;

/**
 * Base exception class for Snek application.
 * Exception thrown for general errors in the Snek application.
 */
public class SnekException extends Exception {

    /**
     * Constructs a SnekException with the specified error message.
     *
     * @param message The error message.
     */
    public SnekException(String message) {
        super(message);
    }
}
