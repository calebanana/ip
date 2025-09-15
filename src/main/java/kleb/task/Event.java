package kleb.task;

import java.time.LocalDateTime;

import kleb.io.Parser;

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
     * Gets the save-formatted string for the Event task.
     *
     * @return The formatted string for file storage.
     */
    @Override
    public String getSaveString() {
        String taskSaveString = super.getSaveString();
        String fromSaveString = Parser.dateToString(this.from, Parser.DateTimeFormat.SAVE);
        String toSaveString = Parser.dateToString(this.to, Parser.DateTimeFormat.SAVE);

        return String.format("E | %s | %s | %s", taskSaveString, fromSaveString, toSaveString);
    }

    /**
     * Gets the display-formatted string for the Event task.
     *
     * @return The formatted string for display.
     */
    @Override
    public String toString() {
        String taskPrintString = super.toString();
        String fromPrintString = Parser.dateToString(this.from, Parser.DateTimeFormat.PRINT);
        String toPrintString = Parser.dateToString(this.to, Parser.DateTimeFormat.PRINT);

        return String.format("[E]%s (from: %s to: %s)",
                taskPrintString, fromPrintString, toPrintString);
    }
}
