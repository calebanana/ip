package kleb.exception;

public class InvalidPriorityException extends Exception {
    public InvalidPriorityException() {
        super();
    }

    @Override
    public String toString() {
        return """
                Uh-oh! The priority level entered is incorrect! Valid values:
                \t1, 2, 3, 0""";
    }
}
