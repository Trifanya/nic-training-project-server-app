package dev.trifanya.tms_server.model.task;

import lombok.Getter;

@Getter
public enum TaskPriority {
    LOW("Низкий"),
    MEDIUM("Средний"),
    HIGH("Высокий");

    private final String ruString;

    TaskPriority(String ruString) {
        this.ruString = ruString;
    }
}
