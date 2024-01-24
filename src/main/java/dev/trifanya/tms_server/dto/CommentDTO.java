package dev.trifanya.tms_server.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Data
public class CommentDTO {
    private int id;

    @NotBlank(message = "Необходимо написать текст комментария.")
    private String text;

    @NotNull
    private int taskId;
}
