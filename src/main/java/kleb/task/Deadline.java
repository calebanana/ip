package kleb.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kleb.io.Parser;

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
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Constructs a new Deadline task with a specific completion status.
     *
     * @param description The description of the task.
     * @param isDone The completion status.
     * @param by The deadline for the task.
     */
    public Deadline(String description, boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Gets the save-formatted string for the Deadline task.
     *
     * @return The formatted string for file storage.
     */
    @Override
    public String getSaveString() {
        String taskSaveString = super.getSaveString();
        String bySaveString = Parser.dateToString(this.by, Parser.DateTimeFormat.SAVE);

        return String.format("D | %s | %s", taskSaveString, bySaveString);
    }

    /**
     * Gets the display-formatted string for the Deadline task.
     *
     * @return The formatted string for display.
     */
    @Override
    public String toString() {
        String taskPrintString = super.toString();
        String byPrintString = Parser.dateToString(this.by, Parser.DateTimeFormat.PRINT);

        return String.format("[D]%s (by: %s)", taskPrintString, byPrintString);
    }
}
