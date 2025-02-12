package bob.exception;

/**
 * Exception thrown when an illegal or unrecognized command is encountered.
 * This exception decorates the error message with horizontal lines for better visibility
 * in the console output.
 */
public class IllegalCommandException extends Exception {

    /**
     * Constructs a new IllegalCommandException with a decorated message.
     * The provided message is wrapped with horizontal lines above and below.
     *
     * @param message the error message describing the illegal command
     */
    public IllegalCommandException(String message) {
        super(message);
    }
}