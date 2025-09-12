package kleb.io;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import kleb.exception.InvalidDateTimeException;

/**
 * Handles parsing of user input and date-time strings.
 */
public class Parser {
    private final Scanner scanner;
    private static final DateTimeFormatter SAVE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd " + "HHmm");
    private static final DateTimeFormatter PRINT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    public enum DateTimeFormat {
        SAVE,
        PRINT
    }

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
     * Converts a LocalDateTime object to a formatted string.
     *
     * @param date The LocalDateTime to format.
     * @param format The format to convert the date into.
     * @return The formatted date-time string.
     */
    public static String dateToString(LocalDateTime date, DateTimeFormat format) {
        switch (format) {
            case SAVE -> {
                return date.format(Parser.SAVE_FORMAT);
            }
            case PRINT -> {
                return date.format(Parser.PRINT_FORMAT);
            }
            default -> {
                return "Invalid date.";
            }
        }
    }

    /**
     * Converts a date-time string to a LocalDateTime object.
     * The expected format is "yyyy-MM-dd HHmm".
     *
     * @param dateTimeString The string to convert.
     * @return The corresponding LocalDateTime object.
     * @throws InvalidDateTimeException If the string format is invalid.
     */
    public static LocalDateTime stringToDateTime(String dateTimeString)
            throws InvalidDateTimeException {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTimeString, inputFormatter);

        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException();
        }
    }
}
