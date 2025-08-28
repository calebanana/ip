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

    public Ui() {
        this.parser = new Parser();
    }

    public void printLine() {
        System.out.println("____________________________________________________________");
    }

    public void printLogo() {
        System.out.println(LOGO);
    }

    public static void printLoadError() {
        System.out.println("Save file corrupted, starting app with empty list.");
    }

    public void greeting() {
        printLine();
        this.printLogo();
        System.out.println("Hello! I'm " + BOT_NAME + "!");
        System.out.println("What can I do for you?");
        printLine();
    }

    public void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public String getInput() {
        return this.parser.getInput();
    }
}
