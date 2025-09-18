package kleb.exception;

/**
 * Represents an exception thrown when a date-time string does not match the expected format.
 */
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
