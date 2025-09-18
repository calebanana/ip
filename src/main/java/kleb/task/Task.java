package kleb.task;

import kleb.exception.InvalidPriorityException;

/**
 * Represents an abstract task with a description and a completion status.
 * This is the base class for ToDo, Deadline, and Event tasks.
 */
public abstract class Task {
    protected final String description;
    protected boolean isDone = false;
    protected TaskPriority priority;

    /**
     * Constructs a new Task with a description.
     * The task is initially marked as not done.
     *
     * @param description The task's description.
     */
    public Task(String description, TaskPriority priority) {
        this.description = description;
        assert !description.isEmpty() : "Description should not be empty.";
        this.priority = priority;
    }

    /**
     * Constructs a new Task with a description and a specific completion status.
     *
     * @param description The task's description.
     * @param isDone The completion status of the task.
     */
    public Task(String description, TaskPriority priority, boolean isDone) {
        this.description = description;
        assert !description.isEmpty() : "Description should not be empty.";
        this.priority = priority;
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

    public String getPriorityStr() {
        return this.priority.toString();
    }

    public int getPriorityLevel() {
        return this.priority.getPriorityLevel();
    }

    public void setPriority(int priorityLevel) throws InvalidPriorityException{
        try {
            this.priority = TaskPriority.getPriorityFromInt(priorityLevel);
        } catch (InvalidPriorityException e) {
            throw new InvalidPriorityException();
        }
    }

    /**
     * Marks the task as done.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void setUndone() {
        this.isDone = false;
    }

    /**
     * Returns true if description has keyword in it.
     * Returns false otherwise.
     *
     * @param keyword to check.
     * @return true if keyword in description.
     */
    public boolean containsKeyword(String keyword) {
        return this.description.contains(keyword);
    }

    /**
     * Gets the string representation of the task for saving to a file.
     *
     * @return A formatted string for file storage.
     */
    public String getSaveString() {
        return String.format("%d | %s | %s", getPriorityLevel(), getStatusIcon(), this.description);
    }

    /**
     * Returns the string representation of the task for display.
     *
     * @return A formatted string for display to the user.
     */
    @Override
    public String toString() {
        return String.format("[%s][%s] %s", getPriorityStr(), getStatusIcon(),
                    this.description);
    }
}
