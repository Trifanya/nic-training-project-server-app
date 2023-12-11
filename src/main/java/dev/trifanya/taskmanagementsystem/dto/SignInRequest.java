package dev.trifanya.taskmanagementsystem.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SignInRequest {
    private String email;
    private String password;
}
