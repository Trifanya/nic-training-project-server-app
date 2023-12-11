package dev.trifanya.taskmanagementsystem.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.repository.UserRepository;
import dev.trifanya.taskmanagementsystem.exception.AlreadyExistException;

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
        User namesake = userRepository.findByEmail(email).orElse(null);
        if (namesake != null && namesake.getId() != currentUserId) {
            throw new AlreadyExistException("Пользователь с указанным email уже существует.");
        }
    }
}
