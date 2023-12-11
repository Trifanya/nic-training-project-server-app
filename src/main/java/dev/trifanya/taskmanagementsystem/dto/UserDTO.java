package dev.trifanya.taskmanagementsystem.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

@Data
public class UserDTO {
    @NotBlank(message = "Необходимо указать имя.")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Имя должно состоять только из букв.")
    private String name;

    @NotBlank(message = "Необходимо указать фамилию.")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Фамилия должна состоять только из букв.")
    private String surname;

    @NotBlank(message = "Необходимо указать должность.")
    private String position;

    @NotBlank(message = "Необходимо указать email.")
    @Email(message = "Необходимо указать корректный email.")
    private String email;

    @NotBlank(message = "Необходимо указать пароль.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#&()–_[{}]():;',?/*~$^+=<>]).{8,}$",
            message = "Пароль должен содержать хотя бы 1 строчную букву, 1 прописную букву, 1 цифру и 1 служебный символ. \n" +
                    "Минимальная длина пароля - 8 символов. ")
    private String password;
}
