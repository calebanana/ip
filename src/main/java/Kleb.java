import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kleb {
    private static final String LOGO = """
            
            ██╗  ██╗██╗     ███████╗██████╗\s
            ██║ ██╔╝██║     ██╔════╝██╔══██╗
            █████╔╝ ██║     █████╗  ██████╔╝
            ██╔═██╗ ██║     ██╔══╝  ██╔══██╗
            ██║  ██╗███████╗███████╗██████╔╝
            ╚═╝  ╚═╝╚══════╝╚══════╝╚═════╝\s
                                           \s""";
    private static final String BOT_NAME = "Kleb";
    private static List<String> tasks = new ArrayList<>();

    public static void line() {
        System.out.println("____________________________________________________________");
    }

    public static void hello() {
        line();
        System.out.println(LOGO);
        System.out.println("Hello! I'm " + BOT_NAME + "!");
        System.out.println("What can I do for you?");
        line();
    }

    public static void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void addTask(String task) {
        tasks.add(task);
        System.out.println("added: " + task);
    }

    public static void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, tasks.get(i)));
        }
    }

    public static void echo() {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            input = scanner.nextLine().toLowerCase();
            line();

            switch (input) {
                case "bye" -> goodbye();
                case "list" -> listTasks();
                default -> addTask(input);
            }

            line();
        } while (!(input.equals("bye")));
    }

    public static void main(String[] args) {
        hello();
        echo();
    }
}
