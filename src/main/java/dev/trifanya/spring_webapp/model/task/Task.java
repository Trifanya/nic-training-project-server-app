package dev.trifanya.spring_webapp.model.task;

import dev.trifanya.spring_webapp.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Task implements Serializable {
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
