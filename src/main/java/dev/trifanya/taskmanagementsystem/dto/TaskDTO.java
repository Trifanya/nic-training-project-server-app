package dev.trifanya.taskmanagementsystem.dto;

import dev.trifanya.taskmanagementsystem.model.task.TaskPriority;
import dev.trifanya.taskmanagementsystem.model.task.TaskStatus;
import lombok.Data;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private UserDTO author;
    private UserDTO performer;
}
