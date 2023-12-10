package dev.trifanya.taskmanagementsystem.validator;

import dev.trifanya.taskmanagementsystem.dto.UserDTO;
import dev.trifanya.taskmanagementsystem.exception.AlreadyExistException;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;
    public void validateNewUser(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AlreadyExistException("Пользователь с указанным email уже существует.");
        }
    }

    public void validateUpdatedUser(int currentUserId, String email) {
        User namesake = userRepository.findByEmail(email).get();
        if (namesake != null && namesake.getId() != currentUserId) {
            throw new AlreadyExistException("Пользователь с указанным email уже существует.");
        }
    }
}
