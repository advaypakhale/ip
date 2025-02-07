package bob.exceptions;

public class IllegalCommandException extends Exception {
    public IllegalCommandException(String message) {
        super("-----------------------------\n" + message + "\n-----------------------------");
    }
}
