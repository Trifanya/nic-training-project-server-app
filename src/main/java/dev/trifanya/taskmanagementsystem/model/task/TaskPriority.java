package dev.trifanya.taskmanagementsystem.model.task;

public enum TaskPriority {
    LOW("Низкий"),
    MEDIUM("Средний"),
    HIGH("Высокий");

    private final String ruString;
    TaskPriority(String ruString) {
        this.ruString = ruString;
    }

    public String getRuString() {
        return this.ruString;
    }
}
