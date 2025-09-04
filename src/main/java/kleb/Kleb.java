package kleb;

import kleb.io.Ui;
import kleb.storage.Storage;
import kleb.task.TaskList;

/**
 * The main class for the Kleb chatbot application.
 * It initializes the application and runs the main command loop.
 */
public class Kleb {
    private static final String SAVE_FILE_PATH = "./data/tasks.txt";
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;


    /**
     * Constructs a new Kleb instance, initializing UI, storage, and task list.
     * Attempts to load tasks from filePath, otherwise creates an empty list.
     *
     * @param filePath The path to the file where tasks are stored.
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
     * Starts the main application loop to handle user input and commands.
     * The loop terminates on 'bye' and saves tasks after modifications.
     */
    public void run() {
        this.ui.greeting();

        String input;

        do {
            input = ui.getInput();
            this.ui.printLine();
            System.out.println(handleCommand(input));
        } while (!input.equals("bye"));
    }

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
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return handleCommand(input);
    }

    /**
     * The main entry point of the Kleb application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Kleb(SAVE_FILE_PATH).run();
    }
}
