package simulation;

public class InvalidCodeException extends Exception {
    private static final String EXCEPTION_MESSAGE = "Instructions are not valid.";

    public InvalidCodeException() {
        super(EXCEPTION_MESSAGE);
    }
}
