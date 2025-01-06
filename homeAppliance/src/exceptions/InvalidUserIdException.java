package exceptions;

/**
 * Exception thrown when an invalid user ID is encountered.
 * 
 * @author 24862664
 */
public class InvalidUserIdException extends Exception {

    /**
     * Constructs a new InvalidUserIdException with the specified detail message.
     *
     * @param message the detail message describing the exception
     */
    public InvalidUserIdException(String message) {
        super(message);
    }
}
