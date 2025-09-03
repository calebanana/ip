package kleb.exception;

/**
 * Represents an exception thrown when the command to create a todo task is incorrect.
 */
public class InvalidToDoException extends Exception {
    public InvalidToDoException() {
        super();
    }

    @Override
    public String toString() {
        return """
                Uh-oh! Your command's format is incorrect. Format:
                \ttodo <description>""";
    }
}
