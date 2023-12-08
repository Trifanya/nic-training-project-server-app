package dev.trifanya.taskmanagementsystem.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private UserDTO userDTO;
    private String passwordConfirmation;
}
