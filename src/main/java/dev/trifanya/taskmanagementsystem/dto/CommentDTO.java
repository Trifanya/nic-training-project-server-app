package dev.trifanya.taskmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private int id;
    private String text;
    private TaskDTO task;
    private UserDTO author;
    private LocalDateTime timestamp;
}
