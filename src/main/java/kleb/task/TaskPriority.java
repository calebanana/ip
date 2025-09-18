package kleb.task;

import kleb.exception.InvalidPriorityException;

public enum TaskPriority {
    HIGH("High", 1),
    MEDIUM("Medium", 2),
    LOW("Low", 3),
    NONE(" ", 0);

    private final String priorityStr;
    private final int priorityLevel;

    TaskPriority(String priorityStr, int priorityLevel) {
        this.priorityStr = priorityStr;
        this.priorityLevel = priorityLevel;
    }

    public static TaskPriority getPriorityFromInt(int priorityLevel)
            throws InvalidPriorityException {
        return switch (priorityLevel) {
            case 1 -> HIGH;
            case 2 -> MEDIUM;
            case 3 -> LOW;
            case 0 -> NONE;
            default ->
                    throw new InvalidPriorityException();
        };
    }

    public int getPriorityLevel() {
        return this.priorityLevel;
    }

    @Override
    public String toString() {
        return this.priorityStr;
    }
}
