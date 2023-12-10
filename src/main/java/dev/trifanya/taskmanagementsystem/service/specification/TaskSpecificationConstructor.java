package dev.trifanya.taskmanagementsystem.service.specification;

import dev.trifanya.taskmanagementsystem.exception.InvalidDataException;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TaskSpecificationConstructor {
    public Specification<Task> createTaskSpecification(String dbColumnName, int userId,
                                                       Map<String, String> filters) {
        Specification<Task> taskSpecification = userIdIs(dbColumnName, userId);

        for (Map.Entry<String, String> filter : filters.entrySet()) {
            switch (filter.getKey()) {
                case "beforeDateTime":
                    LocalDateTime beforeDate = LocalDateTime.parse(filter.getValue());
                    taskSpecification.and(dateIsBefore(dbColumnName, beforeDate));
                    break;
                case "afterDateTime":
                    LocalDateTime afterDate = LocalDateTime.parse(filter.getValue());
                    taskSpecification.and(dateIsAfter(dbColumnName, afterDate));
                    break;
                case "dateTimeRange":
                    String[] dates = filter.getValue().split(",");
                    LocalDateTime rangeStart = LocalDateTime.parse(dates[0]);
                    LocalDateTime rangeEnd = LocalDateTime.parse(dates[1]);
                    taskSpecification.and(dateIsBetween(dbColumnName, rangeStart, rangeEnd));
                    break;
                case "priorityValues":
                    Set<String> prioritySet = new HashSet<>(List.of(filter.getValue().split(",")));
                    taskSpecification.and(taskPriorityInSet(prioritySet));
                    break;
                case "statusValues":
                    Set<String> statusSet = new HashSet<>(List.of(filter.getValue().split(",")));
                    taskSpecification.and(taskStatusInSet(statusSet));
                    break;
                default:
                    throw new InvalidDataException("Неверный формат параметра запроса.");
            }
        }
        return taskSpecification;
    }

    /**
     * Ограничение, при котором ID автора задачи или исполнителя задачи должнен быть равен ID,
     * указанному в параметре userId.
     */
    public static Specification<Task> userIdIs(String dbColumnName, int userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(dbColumnName), userId);
    }

    public static Specification<Task> taskPriorityInSet(Set<String> prioritySet) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get("priority")).value(prioritySet);
    }

    public static Specification<Task> taskStatusInSet(Set<String> statusSet) {
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
