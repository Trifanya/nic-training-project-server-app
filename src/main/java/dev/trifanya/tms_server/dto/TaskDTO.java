package dev.trifanya.tms_server.dto;

import lombok.Data;
import javax.validation.constraints.*;
import dev.trifanya.tms_server.model.task.TaskStatus;
import dev.trifanya.tms_server.model.task.TaskPriority;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private int id;

    @NotBlank(message = "Необходимо указать название задачи.")
    private String title;

    @NotBlank(message = "Необходимо указать описание задачи.")
    private String description;

    private String authorEmail;

    @NotNull(message = "Необходимо указать исполнителя задачи.")
    private String performerEmail;

    @Future
    private LocalDateTime deadline;

    private TaskStatus status;

    private TaskPriority priority;

}
