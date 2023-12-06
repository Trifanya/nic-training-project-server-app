package dev.trifanya.taskmanagementsystem.util;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomErrorResponse {
    private String message;
    private LocalDateTime timestamp;

    public CustomErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
