package kleb.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void toString_formatForDisplay_correctFormat() {
        LocalDateTime from = LocalDateTime.of(2025, 9, 1, 13, 0);
        LocalDateTime to = LocalDateTime.of(2025, 9, 5, 19, 30);

        Event event = new Event("Vacation", from, to);

        String expected = "[E][ ] Vacation (from: Sep 01 2025 1300 to: Sep 05 2025 1930)";

        assertEquals(expected, event.toString());
    }

    @Test
    public void getSaveString_formatForSave_correctFormat() {
        LocalDateTime from = LocalDateTime.of(2025, 9, 1, 13, 0);
        LocalDateTime to = LocalDateTime.of(2025, 9, 5, 19, 30);

        Event event = new Event("Vacation", from, to);

        String expected = "E |   | Vacation | 2025-09-01 1300 | 2025-09-05 1930";

        assertEquals(expected, event.getSaveString());
    }

    @Test
    public void toString_formatForDisplay_markedAsDone() {
        LocalDateTime from = LocalDateTime.of(2025, 9, 1, 13, 0);
        LocalDateTime to = LocalDateTime.of(2025, 9, 5, 19, 30);

        Event event = new Event("Vacation", true, from, to);

        String expected = "[E][X] Vacation (from: Sep 01 2025 1300 to: Sep 05 2025 1930)";

        assertEquals(expected, event.toString());
    }

    @Test
    public void getSaveString_formatForSave_markedAsDone() {
        LocalDateTime from = LocalDateTime.of(2025, 9, 1, 13, 0);
        LocalDateTime to = LocalDateTime.of(2025, 9, 5, 19, 30);

        Event event = new Event("Vacation", true, from, to);

        String expected = "E | X | Vacation | 2025-09-01 1300 | 2025-09-05 1930";

        assertEquals(expected, event.getSaveString());
    }
}
