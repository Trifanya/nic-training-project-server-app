package dev.trifanya.tms_server.validator;

import dev.trifanya.tms_server.mybatis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.exception.AlreadyExistException;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserMapper userMapper;
    public void validateNewUser(String email) {
        if (userMapper.findUserByEmail(email).isPresent()) {
            throw new AlreadyExistException("Пользователь с указанным email уже существует.");
        }
    }

    public void validateUpdatedUser(int currentUserId, String email) {
        User namesake = userMapper.findUserByEmail(email).orElse(null);
        if (namesake != null && namesake.getId() != currentUserId) {
            throw new AlreadyExistException("Пользователь с указанным email уже существует.");
        }
    }
}
