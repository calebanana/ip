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
    private static final List<Task> tasks = new ArrayList<>();

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

    public static void addTask(String input) {
        tasks.add(new Task(input));
        System.out.println("added: " + input);
    }

    public static void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, tasks.get(i)));
        }
    }

    public static void markTask(String input) {
        String taskNo = input.substring(5).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            task.mark();
            System.out.println("Good job! This task has been marked as done:\n" + task);

        } catch (NumberFormatException e) {
            addTask(input);
        }
    }

    public static void unmarkTask(String input) {
        String taskNo = input.substring(7).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            task.unmark();
            System.out.println("Okay! This task has been marked as undone:\n" + task);

        } catch (NumberFormatException e) {
            addTask(input);
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
                default -> {
                    if (input.startsWith("mark ")) {
                        markTask(input);
                    } else if (input.startsWith("unmark ")) {
                        unmarkTask(input);
                    } else {
                        addTask(input);
                    }
                }
            }

            line();
        } while (!(input.equals("bye")));
    }

    public static void main(String[] args) {
        hello();
        echo();
    }
}
