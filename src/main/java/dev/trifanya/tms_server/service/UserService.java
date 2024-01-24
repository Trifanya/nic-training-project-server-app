package dev.trifanya.tms_server.service;

import dev.trifanya.tms_server.mybatis.mapper.UserMapper;
import lombok.Data;
import org.springframework.stereotype.Service;
import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.exception.NotFoundException;

import java.util.List;

@Data
@Service
public class UserService /*implements UserDetailsService*/ {
    private final UserMapper userMapper;

    public User getUserByEmail(String username) {
        return userMapper.findUserByEmail(username)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным email не найден."));
    }

    public User getUser(int userId) {
        return userMapper.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным id не найден."));
    }

    public List<User> getAllUsers() {
        return userMapper.findAllUsers();
    }

    public void createNewUser(User userToSave) {
        /*return userMapper.save(userToSave
                .setPassword(encoder.encode(userToSave.getPassword())));*/
        userMapper.saveUser(userToSave);
    }

    public void updateUserInfo(int userToUpdateId, User updatedUser) {
        /*return userMapper.save(updatedUser
                .setId(userToUpdateId)
                .setPassword(encoder.encode(updatedUser.getPassword())));*/
        userMapper.updateUser(updatedUser
                .setId(userToUpdateId)
                .setPassword(updatedUser.getPassword()));
    }

    public void deleteUser(int userToDeleteId) {
        userMapper.deleteUserById(userToDeleteId);
    }
}
