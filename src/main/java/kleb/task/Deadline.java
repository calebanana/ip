package kleb.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a description and a deadline.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Constructs a new Deadline task.
     *
     * @param description The description of the task.
     * @param by The deadline for the task.
     */
    public Deadline(String description, TaskPriority priority, LocalDateTime by) {
        super(description, priority);
        this.by = by;
    }

    /**
     * Constructs a new Deadline task with a specific completion status.
     *
     * @param description The description of the task.
     * @param isDone The completion status.
     * @param by The deadline for the task.
     */
    public Deadline(String description, TaskPriority priority, boolean isDone, LocalDateTime by) {
        super(description, priority, isDone);
        this.by = by;
    }

    /**
     * Converts a LocalDateTime object to a formatted string.
     *
     * @param dateTime The LocalDateTime to format.
     * @param save Determines if the format is for saving or display.
     * @return The formatted date-time string.
     */
    private String dateToString(LocalDateTime dateTime, boolean save) {
        if (save) {
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        }
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HHmm"));
    }

    /**
     * Gets the save-formatted string for the Deadline task.
     *
     * @return The formatted string for file storage.
     */
    @Override
    public String getSaveString() {
        return String.format("D | %s | %s", super.getSaveString(), dateToString(this.by, true));
    }

    /**
     * Gets the display-formatted string for the Deadline task.
     *
     * @return The formatted string for display.
     */
    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), dateToString(this.by, false));
    }
}
