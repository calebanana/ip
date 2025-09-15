package kleb.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs a new Event task.
     *
     * @param description The description of the event.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, TaskPriority priority, LocalDateTime from, LocalDateTime to) {
        super(description, priority);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs a new Event task with a specific completion status.
     *
     * @param description The description of the event.
     * @param isDone The completion status.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, TaskPriority priority, boolean isDone, LocalDateTime from,
                 LocalDateTime to) {
        super(description, priority, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Converts a LocalDateTime object to a formatted string.
     *
     * @param date The LocalDateTime to format.
     * @param save Determines if the format is for saving or display.
     * @return The formatted date-time string.
     */
    private String dateToString(LocalDateTime date, boolean save) {
        if (save) {
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        }
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy HHmm"));
    }

    /**
     * Gets the save-formatted string for the Event task.
     *
     * @return The formatted string for file storage.
     */
    @Override
    public String getSaveString() {
        return String.format("E | %s | %s | %s", super.getSaveString(),
                dateToString(this.from, true), dateToString(this.to, true));
    }

    /**
     * Gets the display-formatted string for the Event task.
     *
     * @return The formatted string for display.
     */
    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(),
                dateToString(this.from, false), dateToString(this.to, false));
    }
}
