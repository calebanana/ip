package kleb.task;

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

    public static TaskPriority getPriorityFromInt(int priorityLevel) {
        return switch (priorityLevel) {
            case 1 -> HIGH;
            case 2 -> MEDIUM;
            case 3 -> LOW;
            case 0 -> NONE;
            default ->
                    throw new IllegalArgumentException("Invalid priority level: " + priorityLevel);
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
