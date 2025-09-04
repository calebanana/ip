package kleb.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kleb.exception.InvalidDateTimeException;
import kleb.exception.InvalidDeadlineException;
import kleb.exception.InvalidEventException;
import kleb.exception.InvalidToDoException;
import kleb.io.Parser;
import kleb.io.Ui;

/**
 * Manages the list of tasks, including adding, deleting, and modifying them.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList from a list of strings read from a save file.
     *
     * @param taskList The list of strings from the save file.
     */
    public TaskList(List<String> taskList) {
        this.tasks = new ArrayList<>();

        for (String line : taskList) {
            String[] task = line.split("\\|");

            try {
                String type = task[0].trim();
                boolean isDone = task[1].trim().equals("X");
                String description = task[2].trim();

                switch (type) {
                    case "T" -> tasks.add(new ToDo(description, isDone));
                    case "D" -> tasks.add(new Deadline(description, isDone,
                            Parser.stringToDateTime(task[3].trim())));
                    case "E" -> tasks.add(new Event(description, isDone,
                            Parser.stringToDateTime(task[3].trim()),
                            Parser.stringToDateTime(task[4].trim())));
                    default -> Ui.printLoadError();
                }
            } catch (Exception e) {
                Ui.printLoadError();
                tasks.clear();
            }
        }
    }

    /**
     * Prints all tasks in the list to the console.
     */
    public String printList() {
        String printStr = "Here are the tasks in your list:\n";
        for (int i = 0; i < tasks.size(); i++) {
            printStr += String.format("%d. %s", i + 1, tasks.get(i)) + "\n";
        }
        return printStr;
    }

    /**
     * Marks a specific task as done based on user input.
     *
     * @param input The user command, e.g., "mark 2".
     */
    public String markTask(String input) {
        String taskNo = input.substring(4).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            task.setDone();
            return """
                    Good job! This task has been marked as done:
                    \t""" + task;

        } catch (NumberFormatException e) {
            return "Uh-oh! Input is invalid!";
        } catch (IndexOutOfBoundsException e) {
            return "Uh-oh! Enter a number within the list.";
        }
    }

    /**
     * Marks a specific task as not done based on user input.
     *
     * @param input The user command, e.g., "unmark 2".
     */
    public String unmarkTask(String input) {
        String taskNo = input.substring(6).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            task.setUndone();
            return """
                    Okay! This task has been marked as undone:
                    \t""" + task;

        } catch (NumberFormatException e) {
            return "Uh-oh! Input is invalid!";
        } catch (IndexOutOfBoundsException e) {
            return "Uh-oh! Enter a number within the list.";
        }
    }

    /**
     * Adds a new task to the list and prints a confirmation message.
     *
     * @param task The task to be added.
     */
    public String addTask(Task task) {
        tasks.add(task);
        return String.format("""
                        Added a task to your list:
                        \t%s
                        Now you have %d task(s) in the list.
                        """, task, tasks.size());
    }

    /**
     * Parses user input to create and add a new ToDo task.
     *
     * @param input The full user command for adding a todo.
     * @throws InvalidToDoException If the input format is invalid.
     */
    public String addTodo(String input) throws InvalidToDoException {
        String description = input.substring(4).trim();

        if (description.isEmpty()) {
            throw new InvalidToDoException();
        } else {
            return addTask(new ToDo(description));
        }
    }

    /**
     * Parses user input to create and add a new Deadline task.
     *
     * @param input The full user command for adding a deadline.
     * @throws InvalidDeadlineException If the input format is invalid.
     */
    public String addDeadline(String input) throws InvalidDeadlineException {
        String content = input.substring(8).trim();

        String[] parts = content.split("/by", 2);
        if (parts.length < 2) {
            throw new InvalidDeadlineException();
        }

        String description = parts[0].trim();
        String byStr = parts[1].trim();

        if (description.isEmpty() || byStr.isEmpty()) {
            throw new InvalidDeadlineException();
        }

        try {
            LocalDateTime by = Parser.stringToDateTime(byStr);
            return addTask(new Deadline(description, by));
        } catch (InvalidDateTimeException e) {
            return e.toString();
        }
    }

    /**
     * Parses user input to create and add a new Event task.
     *
     * @param input The full user command for adding an event.
     * @throws InvalidEventException If the input format is invalid.
     */
    public String addEvent(String input) throws InvalidEventException {
        String content = input.substring(5).trim();

        String[] descriptionParts = content.split("/from", 2);
        if (descriptionParts.length < 2) {
            throw new InvalidEventException();
        }

        String[] timeParts = descriptionParts[1].split("/to", 2);
        if (timeParts.length < 2) {
            throw new InvalidEventException();
        }

        String description = descriptionParts[0].trim();
        String fromStr = timeParts[0].trim();
        String toStr = timeParts[1].trim();

        if (description.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
            throw new InvalidEventException();
        }

        try {
            LocalDateTime from = Parser.stringToDateTime(fromStr);
            LocalDateTime to = Parser.stringToDateTime(toStr);

            return addTask(new Event(description, from, to));

        } catch (InvalidDateTimeException e) {
            return e.toString();
        }
    }

    /**
     * Deletes a task from the list based on user input.
     *
     * @param input The user command, e.g., "delete 2".
     */
    public String deleteTask(String input) {
        String taskNo = input.substring(6).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            tasks.remove(task);
            return """
                    Poof! This task has been deleted:
                    \t""" + task;

        } catch (NumberFormatException e) {
            return "Uh-oh! Input is invalid!";
        } catch (IndexOutOfBoundsException e) {
            return "Uh-oh! Enter a number within the list.";
        }
    }

    /**
     * Filter and find tasks based on keyword.
     *
     * @param input keyword to filter by.
     */
    public String findTasks(String input) {
        String keyword = input.substring(4).trim();
        List<Task> matchTasks = new ArrayList<>();

        for (Task task : this.tasks) {
            if (task.containsKeyword(keyword)) {
                matchTasks.add(task);
            }
        }

        String printStr = "Here are the matching tasks in your list:\n";
        for (int i = 0; i < matchTasks.size(); i++) {
            printStr += String.format("%d. %s", i + 1, matchTasks.get(i)) + "\n";
        }

        return printStr;
    }

    /**
     * Gets a list of all tasks formatted as strings for saving.
     *
     * @return A list of save-formatted task strings.
     */
    public List<String> getSaveList() {
        List<String> saveList = new ArrayList<>();

        for (Task task : tasks) {
            saveList.add(task.getSaveString());
        }

        return saveList;
    }
}
