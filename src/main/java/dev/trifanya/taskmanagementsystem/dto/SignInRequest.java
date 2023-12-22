package dev.trifanya.taskmanagementsystem.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class SignInRequest {
    @Email(message = "Введите корректный email.")
    private String email;

    @NotBlank(message = "Вы не ввели пароль.")
    private String password;
}
