package dev.trifanya.taskmanagementsystem.model.task;

public enum TaskStatus {
    NOT_STARTED("Не начата"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Выполнена");

    public final String ruString;

    TaskStatus(String ruString) {
        this.ruString = ruString;
    }

    public String getRuString() {
        return ruString;
    }
}
