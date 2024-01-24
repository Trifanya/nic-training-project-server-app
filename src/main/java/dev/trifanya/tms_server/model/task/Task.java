package dev.trifanya.tms_server.model.task;

import lombok.Data;
import lombok.experimental.Accessors;
import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.model.Comment;

import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Task {
    private int id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDateTime createdAt;

    private LocalDateTime deadline;

    private User author;

    private User performer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                status == task.status &&
                priority == task.priority &&
                Objects.equals(createdAt, task.createdAt) &&
                Objects.equals(deadline, task.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, priority, createdAt, deadline);
    }
}
