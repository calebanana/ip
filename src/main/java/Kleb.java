import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kleb {
    private static final String LOGO = """
             _    _      _    
            | | _| | ___| |__ 
            | |/ / |/ _ \\ '_ \\
            |   <| |  __/ |_) |
            |_|\\_\\_|\\___|_.__/
                                           """;
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

    public static void addTask(Task task) {
        tasks.add(task);
        System.out.println("Added a task to your list:\n\t" + task);
        System.out.println(String.format("Now you have %d task(s) in the list.", tasks.size()));
    }

    public static void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, tasks.get(i)));
        }
    }

    public static void markTask(String input) {
        String taskNo = input.substring(4).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            task.mark();
            System.out.println("Good job! This task has been marked as done:\n\t" + task);

        } catch (NumberFormatException e) {
            System.out.println("Uh-oh! Input is invalid!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Uh-oh! Enter a number within the list.");
        }
    }

    public static void unmarkTask(String input) {
        String taskNo = input.substring(6).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            task.unmark();
            System.out.println("Okay! This task has been marked as undone:\n\t" + task);

        } catch (NumberFormatException e) {
            System.out.println("Uh-oh! Input is invalid!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Uh-oh! Enter a number within the list.");
        }
    }

    public static void addTodo(String input) throws InvalidToDoException {
        String description = input.substring(4).trim();

        if (description.equals("")) {
            throw new InvalidToDoException();
        } else {
            addTask(new ToDo(description));
        }
    }

    public static void addDeadline(String input) throws InvalidDeadlineException {
        String content = input.substring(8).trim();
        String[] parts = content.split("/by", 2);

        if (parts.length != 2) {
            throw new InvalidDeadlineException();
        } else {
            String description = parts[0].trim();
            String by = parts[1].trim();

            if (description.equals("") || by.equals("")) {
                throw new InvalidDeadlineException();
            } else {
                addTask(new Deadline(description, by));
            }
        }
    }

    public static void addEvent(String input) throws InvalidEventException {
        String content = input.substring(5).trim();
        String[] front = content.split("/from", 2);

        if (front.length == 2) {
            String[] back = front[1].split("/to", 2);
            if (back.length == 2) {
                String description = front[0].trim();
                String from = back[0].trim();
                String to = back[1].trim();

                if (description.equals("") || from.equals("") || to.equals("")) {
                    throw new InvalidEventException();
                } else {
                    addTask(new Event(description, from, to));
                }
            } else {
                throw new InvalidEventException();
            }
        } else {
            throw new InvalidEventException();
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
                    try {
                        if (input.startsWith("mark")) {
                            markTask(input);
                        } else if (input.startsWith("unmark")) {
                            unmarkTask(input);
                        } else if (input.startsWith("todo")) {
                            addTodo(input);
                        } else if (input.startsWith("deadline")) {
                            addDeadline(input);
                        } else if (input.startsWith("event")) {
                            addEvent(input);
                        } else {
                            System.out.println("""
                                    Hmm, I don't quite understand your input.
                                    Available commands:
                                    mark, unmark, todo, deadline, event, list, bye""");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
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
