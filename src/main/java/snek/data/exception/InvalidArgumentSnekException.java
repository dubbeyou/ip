package snek.data.exception;

/**
 * Exception thrown for invalid arguments in the Snek application.
 */
public class InvalidArgumentSnekException extends SnekException {
    public InvalidArgumentSnekException(String message) {
        super(message);
    }
}
