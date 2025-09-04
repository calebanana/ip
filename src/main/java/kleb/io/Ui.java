package kleb.io;

/**
 * Handles all user interface interactions for the application.
 * This includes displaying messages, logos, and getting user input.
 */
public class Ui {
    private static final String LOGO = """
             _    _      _
            | | _| | ___| |__
            | |/ / |/ _ \\ '_ \\
            |   <| |  __/ |_) |
            |_|\\_\\_|\\___|_.__/
                                           """;
    private static final String BOT_NAME = "Kleb";
    private final Parser parser;

    /**
     * Constructs a new Ui instance.
     * Initializes a parser to handle user input.
     */
    public Ui() {
        this.parser = new Parser();
    }

    /**
     * Prints a horizontal line to the console for visual separation.
     */
    public void printLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints the application's ASCII art logo to the console.
     */
    public void printLogo() {
        System.out.println(LOGO);
    }

    /**
     * Prints an error message indicating the save file is corrupted.
     */
    public static void printLoadError() {
        System.out.println("Save file corrupted, starting app with empty list.");
    }

    /**
     * Prints the initial greeting message to the user.
     */
    public void greeting() {
        printLine();
        this.printLogo();
        System.out.println("Hello! I'm " + BOT_NAME + "!");
        System.out.println("What can I do for you?");
        printLine();
    }

    /**
     * Prints the exit message to the user.
     */
    public String exit() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Gets the next line of input from the user via the parser.
     * @return The user's input string.
     */
    public String getInput() {
        return this.parser.getInput();
    }
}
