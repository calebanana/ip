package kleb.io;

import kleb.exception.InvalidDateTimeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Handles parsing of user input and date-time strings.
 */
public class Parser {
    private final Scanner scanner;

    /**
     * Constructs a new Parser instance.
     * Initializes a scanner to read from standard input.
     */
    public Parser() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of input from the user.
     * @return The user's input string.
     */
    public String getInput() {
        return this.scanner.nextLine();
    }

    /**
     * Converts a date-time string to a LocalDateTime object.
     * The expected format is "yyyy-MM-dd HHmm".
     *
     * @param dateTimeString The string to convert.
     * @return The corresponding LocalDateTime object.
     * @throws InvalidDateTimeException If the string format is invalid.
     */
    public static LocalDateTime stringToDateTime(String dateTimeString) throws InvalidDateTimeException {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTimeString, inputFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException();
        }
    }
}
