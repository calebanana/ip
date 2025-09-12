package kleb.exception;

public class FilePermissionException extends RuntimeException {
    public FilePermissionException() {
        super();
    }

    @Override
    public String toString() {
        return """
                Uh-oh! No permissions to read/write to the directory!""";
    }
}
