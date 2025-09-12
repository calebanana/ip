package kleb;

import kleb.io.Ui;
import kleb.storage.Storage;
import kleb.task.TaskList;

/**
 * Main class for the Kleb application.
 * This class initializes and runs the task management chatbot.
 */
public class Kleb {
    private static final String SAVE_FILE_PATH = "./data/tasks.txt";
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Constructs a new Kleb instance.
     * It initializes the UI, storage, and loads tasks from the specified file path.
     *
     * @param filePath The path to the file where tasks are saved and loaded from.
     */
    public Kleb(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        TaskList temp;

        try {
            temp = new TaskList(storage.readFile());
        } catch (Exception e) {
            Ui.printLoadError();
            temp = new TaskList();
        }
        this.taskList = temp;
    }

    /**
     * Starts the main loop of the Kleb application.
     * It continuously prompts the user for input and processes commands until "bye" is entered.
     */
    public void run() {
        this.ui.greet();

        String input;

        do {
            input = ui.getInput();
            this.ui.printLine();
            System.out.println(handleCommand(input));
        } while (!input.equals("bye"));
        assert input.equals("bye") : "Exit keyword should be 'bye'";
    }

    /**
     * Processes a single user command.
     * It parses the input and delegates the action to the appropriate method in TaskList.
     *
     * @param input The raw user input string.
     * @return A string response to be displayed to the user.
     */
    public String handleCommand(String input) {
        switch (input) {
            case "bye" -> {
                return this.ui.exit();
            }
            case "list" -> {
                return this.taskList.printList();
            }
            default -> {
                try {
                    if (input.startsWith("mark")) {
                        return this.taskList.markTask(input);
                    } else if (input.startsWith("unmark")) {
                        return this.taskList.unmarkTask(input);
                    } else if (input.startsWith("todo")) {
                        return this.taskList.addTodo(input);
                    } else if (input.startsWith("deadline")) {
                        return this.taskList.addDeadline(input);
                    } else if (input.startsWith("event")) {
                        return this.taskList.addEvent(input);
                    } else if (input.startsWith("delete")) {
                        return this.taskList.deleteTask(input);
                    } else if (input.startsWith("find")) {
                        return this.taskList.findTasks(input);
                    } else {
                        return """
                                Hmm, I don't quite understand your input.
                                Available commands:
                                mark, unmark, todo, deadline, event, delete, find, list, bye""";
                    }
                } catch (Exception e) {
                    return e.toString();
                } finally {
                    this.storage.save(this.taskList.getSaveList());
                }
            }
        }
    }

    /**
     * The main entry point of the Kleb application.
     * Creates a new Kleb instance and starts its execution.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Kleb(SAVE_FILE_PATH).run();
    }
}
