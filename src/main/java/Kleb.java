import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private static final String SAVE_DIRECTORY = "./data";
    private static final String SAVE_FILE_PATH = SAVE_DIRECTORY + "/tasks.txt";

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

    public static void deleteTask(String input) {
        String taskNo = input.substring(6).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            tasks.remove(task);
            System.out.println("Poof! This task has been deleted:\n\t" + task);
        } catch (NumberFormatException e) {
            System.out.println("Uh-oh! Input is invalid!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Uh-oh! Enter a number within the list.");
        }
    }

    public static List<String> readFile() {
        File save_dir = new File(SAVE_DIRECTORY);
        File save_file = new File(SAVE_FILE_PATH);

        if (!save_dir.exists()) {
            save_dir.mkdirs();
        }
        if (!save_file.exists()) {
            try {
                save_file.createNewFile();
                System.out.println("Created file.");
            } catch (IOException e) {
                System.out.println("Unable to create save file.");
                return new ArrayList<>();
            }
        }

        try {
            List<String> fileContent = Files.readAllLines(Paths.get(SAVE_FILE_PATH));
            return fileContent;
        } catch (IOException e) {
            System.out.println("Unable to read save file.");
        }
        return new ArrayList<>();
    }

    public static void loadTasks() {
        List<String> fileContent = readFile();
        for (String line : fileContent) {
            String[] task = line.split("\\|");

            try {
                String type = task[0].trim();
                boolean isDone = task[1].trim().equals("X");
                String description = task[2].trim();

                switch (type) {
                    case "T" -> tasks.add(new ToDo(description, isDone));
                    case "D" -> tasks.add(new Deadline(description, isDone, task[3].trim()));
                    case "E" -> tasks.add(new Event(description, isDone, task[3].trim(),
                            task[4].trim()));
                    default -> System.out.println("Save file seems to be corrupted.");
                }
            } catch (Exception e) {
                System.out.println("Save file seems to be corrupted.");
                tasks.clear();
            }
        }
    }

    public static void saveTasks() {
        try (FileWriter fileWriter = new FileWriter(SAVE_FILE_PATH)) {
            for (Task task : tasks) {
                fileWriter.write(task.toSaveString() + "\n");
            }

            System.out.println(String.format("Tasks saved in %s", SAVE_FILE_PATH));
        } catch (IOException e) {
            System.out.println("Error when writing to file.");
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
                        } else if (input.startsWith("delete")) {
                            deleteTask(input);
                        } else {
                            System.out.println("""
                                    Hmm, I don't quite understand your input.
                                    Available commands:
                                    mark, unmark, todo, deadline, event, delete, list, bye""");
                        }
                        saveTasks();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
            line();
        } while (!(input.equals("bye")));
    }

    public static void main(String[] args) {
        loadTasks();
        hello();
        echo();
    }
}
