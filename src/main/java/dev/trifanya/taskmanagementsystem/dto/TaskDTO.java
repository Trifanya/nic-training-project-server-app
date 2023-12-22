package dev.trifanya.taskmanagementsystem.dto;

import lombok.Data;
import javax.validation.constraints.*;
import dev.trifanya.taskmanagementsystem.model.task.TaskStatus;
import dev.trifanya.taskmanagementsystem.model.task.TaskPriority;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private int id;

    @NotBlank(message = "Необходимо указать название задачи.")
    private String title;

    @NotBlank(message = "Необходимо указать описание задачи.")
    private String description;

    @NotNull(message = "Необходимо указать исполнителя задачи.")
    private int performerId;

    @Future
    private LocalDateTime deadline;

    private TaskStatus status;

    private TaskPriority priority;

}
