package kleb.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    private String dateToString(LocalDateTime date, boolean save){
        if (save) {
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        }
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy HHmm"));
    }

    @Override
    public String getSaveString() {
        return String.format("E | %s | %s | %s", super.getSaveString(), dateToString(this.from,
                true), dateToString(this.to, true));
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), dateToString(this.from,
                false), dateToString(this.to, false));
    }
}
