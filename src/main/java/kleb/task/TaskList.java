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
     * Constructs a TaskList from a list of strings from the save file.
     * It parses each string to reconstruct the original tasks.
     *
     * @param taskList A list of strings, each representing a saved task.
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
     * Formats and returns the entire list of tasks as a single string.
     * Each task is numbered and presented on a new line.
     *
     * @return A string representation of all tasks in the list.
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
     * The input is expected to contain the 1-based index of the task.
     *
     * @param input The raw user command.
     * @return A confirmation message or an error string.
     */
    public String markTask(String input) {
        String taskNo = input.substring(4).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            task.setDone();
            assert task.isDone : "isDone should have been set to true.";

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
     * The input is expected to contain the 1-based index of the task.
     *
     * @param input The raw user command.
     * @return A confirmation message or an error string.
     */
    public String unmarkTask(String input) {
        String taskNo = input.substring(6).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            task.setUndone();
            assert !task.isDone : "isDone should have been set to false.";

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
     * Adds a new task to the list and returns a confirmation message.
     * This is a general helper method for adding any type of task.
     *
     * @param task The task object to be added.
     * @return A string confirming the addition and showing the new task count.
     */
    public String addTask(Task task) {
        int numOfTasks = tasks.size();
        tasks.add(task);
        assert tasks.size() == numOfTasks + 1 : "Tasks should have increased by 1.";

        return String.format("""
                        Added a task to your list:
                        \t%s
                        Now you have %d task(s) in the list.
                        """, task, tasks.size());
    }

    /**
     * Parses the input string to create and add a new ToDo task.
     * It extracts the description from the user command.
     *
     * @param input The raw user command.
     * @return A confirmation message from the addTask method.
     * @throws InvalidToDoException If the todo description is empty.
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
     * Parses the input string to create and add a new Deadline task.
     * It extracts the description and the due date.
     *
     * @param input The raw user command.
     * @return A confirmation message from the addTask method.
     * @throws InvalidDeadlineException If the input format is incorrect.
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
     * Parses the input string to create and add a new Event task.
     * It extracts the description and the start/end times.
     *
     * @param input The raw user command.
     * @return A confirmation message from the addTask method.
     * @throws InvalidEventException If the input format is incorrect.
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
     * Deletes a specific task from the list based on user input.
     * The input is expected to contain the 1-based index of the task to delete.
     *
     * @param input The raw user command.
     * @return A confirmation message or an error string.
     */
    public String deleteTask(String input) {
        String taskNo = input.substring(6).trim();

        try {
            int numOfTasks = tasks.size();
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            tasks.remove(task);
            assert tasks.size() == numOfTasks - 1 : "Tasks should have decreased by 1.";

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
     * Finds and lists all tasks containing a specific keyword.
     * The search is case-sensitive.
     *
     * @param input The raw user command containing the keyword.
     * @return A string containing the list of matching tasks.
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
