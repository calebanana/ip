public class InvalidDeadlineException extends Exception {
    public InvalidDeadlineException() {
        super();
    }

    @Override
    public String toString() {
        return """
                Uh-oh! Your command's format is incorrect. Format:
                \tdeadline <description> /by <due date>""";
    }
}
