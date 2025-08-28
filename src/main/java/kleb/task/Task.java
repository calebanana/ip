package kleb.task;

/**
 * Represents an abstract task with a description and a completion status.
 * This is the base class for ToDo, Deadline, and Event tasks.
 */
public abstract class Task {
    protected final String description;
    protected boolean isDone = false;

    /**
     * Constructs a new Task with a description.
     * The task is initially marked as not done.
     *
     * @param description The task's description.
     */
    public Task(String description) {
        this.description = description;
    }

    /**
     * Constructs a new Task with a description and a specific completion status.
     *
     * @param description The task's description.
     * @param isDone The completion status of the task.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Gets the status icon for the task.
     * Returns "X" for done, " " for not done.
     *
     * @return The status icon string.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Gets the string representation of the task for saving to a file.
     *
     * @return A formatted string for file storage.
     */
    public String getSaveString() {
        return String.format("%s | %s", getStatusIcon(), this.description);
    }

    /**
     * Returns the string representation of the task for display.
     *
     * @return A formatted string for display to the user.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), this.description);
    }
}