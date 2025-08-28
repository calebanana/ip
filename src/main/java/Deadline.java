import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private final LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }

    private String dateToString(LocalDateTime dateTime, boolean save){
        if (save) {
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        }
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HHmm"));
    }

    @Override
    public String getSaveString() {
        return String.format("D | %s | %s", super.getSaveString(), dateToString(this.by, true));
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), dateToString(this.by, false));
    }
}
