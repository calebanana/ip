package kleb.exception;

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
