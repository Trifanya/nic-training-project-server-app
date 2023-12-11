package dev.trifanya.taskmanagementsystem.service.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.domain.Specification;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import dev.trifanya.taskmanagementsystem.model.task.TaskStatus;
import dev.trifanya.taskmanagementsystem.model.task.TaskPriority;
import dev.trifanya.taskmanagementsystem.exception.InvalidDataException;

import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskSpecificationConstructor {
    public Specification<Task> createTaskSpecification(String fieldName, User user,
                                                       Map<String, String> filters) {
        Specification<Task> taskSpecification = userIs(fieldName, user);

        for (Map.Entry<String, String> filter : filters.entrySet()) {
            String key = filter.getKey();
            String[] keyParts = key.split("_");
            String dateTimeField = null;
            if (keyParts.length > 1) {
                dateTimeField = keyParts[0];
                key = keyParts[1];
            }
            switch (key) {
                case "beforeDateTime" -> {
                    LocalDateTime beforeDate = LocalDateTime.parse(filter.getValue());
                    taskSpecification = taskSpecification.and(dateIsBefore(dateTimeField, beforeDate));
                }
                case "afterDateTime" -> {
                    LocalDateTime afterDate = LocalDateTime.parse(filter.getValue());
                    taskSpecification = taskSpecification.and(dateIsAfter(dateTimeField, afterDate));
                }
                case "dateTimeRange" -> {
                    String[] dates = filter.getValue().split(",");
                    LocalDateTime rangeStart = LocalDateTime.parse(dates[0]);
                    LocalDateTime rangeEnd = LocalDateTime.parse(dates[1]);
                    taskSpecification = taskSpecification.and(dateIsBetween(dateTimeField, rangeStart, rangeEnd));
                }
                case "priorityValues" -> {
                    Set<TaskPriority> prioritySet = Arrays.stream((filter.getValue().split(",")))
                            .map(TaskPriority::valueOf)
                            .collect(Collectors.toSet());
                    taskSpecification = taskSpecification.and(taskPriorityInSet(prioritySet));
                }
                case "statusValues" -> {
                    Set<TaskStatus> statusSet = Arrays.stream(filter.getValue().split(","))
                            .map(TaskStatus::valueOf)
                            .collect(Collectors.toSet());
                    taskSpecification = taskSpecification.and(taskStatusInSet(statusSet));
                }
                default -> throw new InvalidDataException("Неверный формат параметра запроса.");
            }
        }
        return taskSpecification;
    }

    /**
     * Ограничение, при котором ID автора задачи или исполнителя задачи должнен быть равен ID,
     * указанному в параметре userId.
     */
    public static Specification<Task> userIs(String fieldName, User user) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), user);
    }

    public static Specification<Task> taskPriorityInSet(Set<TaskPriority> prioritySet) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get("priority")).value(prioritySet);
    }

    public static Specification<Task> taskStatusInSet(Set<TaskStatus> statusSet) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get("status")).value(statusSet);
    }

    public static Specification<Task> dateIsBefore(String columnName, LocalDateTime rangeEnd) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get(columnName), rangeEnd);
    }

    public static Specification<Task> dateIsAfter(String columnName, LocalDateTime rangeStart) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get(columnName), rangeStart);
    }

    public static Specification<Task> dateIsBetween(String columnName, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(columnName), rangeStart, rangeEnd);
    }
}
