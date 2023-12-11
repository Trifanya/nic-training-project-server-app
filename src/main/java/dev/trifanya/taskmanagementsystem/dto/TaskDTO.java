package dev.trifanya.taskmanagementsystem.dto;

import dev.trifanya.taskmanagementsystem.model.task.TaskPriority;
import dev.trifanya.taskmanagementsystem.model.task.TaskStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private int id;


    @NotBlank(message = "Необходимо указать название задачи.")
    private String title;

    @NotBlank(message = "Необходимо указать описание задачи.")
    private String description;

    @NotNull
    private int performerId;

    @Future
    private LocalDateTime deadline;

    private TaskStatus status;

    private TaskPriority priority;

}
