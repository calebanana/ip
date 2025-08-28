package kleb.exception;

public class InvalidDateTimeException extends Exception {
    public InvalidDateTimeException() {
        super();
    }

    @Override
    public String toString() {
        return """
                Uh-oh! The date time format is incorrect. Format:
                \tyyyy-MM-dd HHmm""";
    }
}