package kleb.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Contains unit tests for the Deadline class.
 */
public class DeadlineTest {
    /**
     * Tests if the toString method formats an undone deadline correctly for display.
     */
    @Test
    public void toString_formatForDisplay_correctFormat() {
        LocalDateTime by = LocalDateTime.of(2025, 9, 1, 13, 0);
        Deadline deadline = new Deadline("Project", by);

        String expected = "[D][ ] Project (by: Sep 01 2025 1300)";

        assertEquals(expected, deadline.toString());
    }

    /**
     * Tests if the getSaveString method formats an undone deadline correctly for saving.
     */
    @Test
    public void getSaveString_formatForSave_correctFormat() {
        LocalDateTime by = LocalDateTime.of(2025, 9, 1, 13, 0);
        Deadline deadline = new Deadline("Project", by);

        String expected = "D |   | Project | 2025-09-01 1300";

        assertEquals(expected, deadline.getSaveString());
    }

    /**
     * Tests if the toString method formats a completed deadline correctly for display.
     */
    @Test
    public void toString_formatForDisplay_markedAsDone() {
        LocalDateTime by = LocalDateTime.of(2025, 9, 1, 13, 0);
        Deadline deadline = new Deadline("Project", true, by);

        String expected = "[D][X] Project (by: Sep 01 2025 1300)";

        assertEquals(expected, deadline.toString());
    }

    /**
     * Tests if the getSaveString method formats a completed deadline correctly for saving.
     */
    @Test
    public void getSaveString_formatForSave_markedAsDone() {
        LocalDateTime by = LocalDateTime.of(2025, 9, 1, 13, 0);
        Deadline deadline = new Deadline("Project", true, by);

        String expected = "D | X | Project | 2025-09-01 1300";

        assertEquals(expected, deadline.getSaveString());
    }
}
