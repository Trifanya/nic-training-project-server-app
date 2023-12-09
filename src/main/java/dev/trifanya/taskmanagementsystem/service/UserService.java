package dev.trifanya.taskmanagementsystem.service;

import dev.trifanya.taskmanagementsystem.exception.NotFoundException;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) {
        System.out.println("Username: " + username);
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным email не найден."));
    }

    public User getUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным id не найден."));
    }

    public void createNewUser(User userToSave) {
        userRepository.save(userToSave);
    }

    public void updateUserInfo(int userToUpdateId, User updatedUser) {
        updatedUser.setId(userToUpdateId);
        userRepository.save(updatedUser);
    }

    public void deleteUser(int userToDeleteId) {
        userRepository.deleteById(userToDeleteId);
    }



}
