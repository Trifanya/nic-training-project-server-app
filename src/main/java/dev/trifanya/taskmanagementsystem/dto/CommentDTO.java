package dev.trifanya.taskmanagementsystem.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
public class CommentDTO {
    private int id;

    @NotBlank(message = "Необходимо написать текст комментария.")
    private String text;

    @NotNull
    private int taskId;
}
