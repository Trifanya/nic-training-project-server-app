package dev.trifanya.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private int id;

    @NotBlank(message = "Необходимо написать текст комментария.")
    private String text;

    @NotNull
    private int taskId;
}
