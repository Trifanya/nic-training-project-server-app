package dev.trifanya.taskmanagementsystem.controller;

import dev.trifanya.taskmanagementsystem.dto.UserDTO;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.service.UserService;
import dev.trifanya.taskmanagementsystem.util.MainClassConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MainClassConverter converter;

    @GetMapping("/profile")
    public UserDTO getUserProfile(@AuthenticationPrincipal User currentUser) {
        return converter.convertToUserDTO(currentUser);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody UserDTO userDTO) {
        userService.createNewUser(converter.convertToUser(userDTO));

        return ResponseEntity.ok("Регистрация прошла успешно.");
    }

    @PatchMapping("/profile/updateUser")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserDTO userDTO,
                                            @AuthenticationPrincipal User currentUser) {
        userService.updateUserInfo(
                currentUser.getId(),
                converter.convertToUser(userDTO)
        );

        return ResponseEntity.ok("Данные успешно отредактированы.");
    }

    @DeleteMapping("/{id}/deleteUser")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int userToDeleteId) {
        userService.deleteUser(userToDeleteId);

        return ResponseEntity.ok("Пользователь успешно удален.");
    }
}
