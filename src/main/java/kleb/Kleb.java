package kleb;

import kleb.io.Ui;
import kleb.storage.Storage;
import kleb.task.TaskList;

/**
 * The main class for the Kleb chatbot application.
 * It initializes the application and runs the main command loop.
 */
public class Kleb {
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

            switch (input) {
                case "bye" -> this.ui.exit();
                case "list" -> this.taskList.printList();
                default -> {
                    try {
                        if (input.startsWith("mark")) {
                            this.taskList.markTask(input);
                        } else if (input.startsWith("unmark")) {
                            this.taskList.unmarkTask(input);
                        } else if (input.startsWith("todo")) {
                            this.taskList.addTodo(input);
                        } else if (input.startsWith("deadline")) {
                            this.taskList.addDeadline(input);
                        } else if (input.startsWith("event")) {
                            this.taskList.addEvent(input);
                        } else if (input.startsWith("delete")) {
                            this.taskList.deleteTask(input);
                        } else {
                            System.out.println("""
                                    Hmm, I don't quite understand your input.
                                    Available commands:
                                    mark, unmark, todo, deadline, event, delete, list, bye""");
                        }
                        this.storage.save(this.taskList.getSaveList());
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
            this.ui.printLine();
        } while (!(input.equals("bye")));
    }

    /**
     * The main entry point of the Kleb application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Kleb("./data/tasks.txt").run();
    }
}
