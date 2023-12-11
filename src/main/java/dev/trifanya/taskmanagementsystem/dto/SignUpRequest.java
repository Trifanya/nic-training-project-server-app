package dev.trifanya.taskmanagementsystem.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class SignUpRequest {
    @Valid
    private UserDTO userDTO;

    @NotBlank(message = "Необходимо подтвердить пароль.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#&()–_[{}]():;',?/*~$^+=<>]).{8,}$",
            message = "Пароль должен содержать хотя бы 1 строчную букву, 1 прописную букву, 1 цифру и 1 служебный символ. \n" +
                    "Минимальная длина пароля - 8 символов. ")
    private String passwordConfirmation;
}
