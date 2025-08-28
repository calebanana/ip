import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Parser {
    private final Scanner scanner;

    public Parser() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput() {
        return this.scanner.nextLine();
    }

    public static LocalDateTime stringToDateTime(String dateTimeString) throws InvalidDateTimeException {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTimeString, inputFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException();
        }
    }
}
