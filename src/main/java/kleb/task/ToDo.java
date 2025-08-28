package kleb.task;

/**
 * Represents a ToDo task, which is a basic task with only a description.
 */
public class ToDo extends Task {
    /**
     * Constructs a new ToDo task.
     *
     * @param description The description of the ToDo.
     */
    public ToDo(String description){
        super(description);
    }

    /**
     * Constructs a new ToDo task with a specific completion status.
     *
     * @param description The description of the ToDo.
     * @param isDone The completion status.
     */
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Gets the save-formatted string for the ToDo task.
     *
     * @return The formatted string for file storage.
     */
    @Override
    public String getSaveString() {
        return String.format("T | %s", super.getSaveString());
    }

    /**
     * Gets the display-formatted string for the ToDo task.
     *
     * @return The formatted string for display.
     */
    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
