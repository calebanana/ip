package kleb.task;

import kleb.exception.InvalidDateTimeException;
import kleb.exception.InvalidDeadlineException;
import kleb.exception.InvalidEventException;
import kleb.exception.InvalidToDoException;
import kleb.io.Parser;
import kleb.io.Ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

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

    public void printList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, tasks.get(i)));
        }
    }

    public void markTask(String input) {
        String taskNo = input.substring(4).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            task.setDone();
            System.out.println("Good job! This task has been marked as done:\n\t" + task);

        } catch (NumberFormatException e) {
            System.out.println("Uh-oh! Input is invalid!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Uh-oh! Enter a number within the list.");
        }
    }

    public void unmarkTask(String input) {
        String taskNo = input.substring(6).trim();

        try {
            int taskIdx = Integer.parseInt(taskNo);
            Task task = tasks.get(taskIdx - 1);
            task.setUndone();
            System.out.println("Okay! This task has been marked as undone:\n\t" + task);

        } catch (NumberFormatException e) {
            System.out.println("Uh-oh! Input is invalid!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Uh-oh! Enter a number within the list.");
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Added a task to your list:\n\t" + task);
        System.out.println(String.format("Now you have %d task(s) in the list.", tasks.size()));
    }

    public void addTodo(String input) throws InvalidToDoException {
        String description = input.substring(4).trim();

        if (description.equals("")) {
            throw new InvalidToDoException();
        } else {
            addTask(new ToDo(description));
        }
    }

    public void addDeadline(String input) throws InvalidDeadlineException {
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
            addTask(new Deadline(description, by));
        } catch (InvalidDateTimeException e) {
            System.out.println(e);
        }
    }

    public void addEvent(String input) throws InvalidEventException {
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

            addTask(new Event(description, from, to));

        } catch (InvalidDateTimeException e) {
            System.out.println(e);
        }
    }

    public void deleteTask(String input) {
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

    public List<String> getSaveList() {
        List<String> saveList = new ArrayList<>();

        for (Task task : tasks){
            saveList.add(task.getSaveString());
        }

        return saveList;
    }
}
