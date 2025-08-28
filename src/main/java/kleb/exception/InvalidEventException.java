package kleb.exception;

public class InvalidEventException extends Exception {
    public InvalidEventException() {
        super();
    }

    @Override
    public String toString() {
        return """
                Uh-oh! Your command's format is incorrect. Format:
                \tevent <description> /from <start> /to <end>""";
    }
}
