package dev.trifanya.taskmanagementsystem.service.specification;

import dev.trifanya.taskmanagementsystem.model.task.Task;
import dev.trifanya.taskmanagementsystem.model.task.TaskPriority;
import dev.trifanya.taskmanagementsystem.model.task.TaskStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Component
public class TaskSpecificationConstructor {
    public Specification<Task> createTaskSpecification(Map<String, String> filters) {
        Specification<Task> taskSpecification = null;
        return taskSpecification;
    }

    public static Specification<Task> taskPriorityInSet(Set<TaskPriority> priorities) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get("priority")).value(priorities);
    }

    public static Specification<Task> taskStatusInSet(Set<TaskStatus> statuses) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get("status")).value(statuses);
    }
}
