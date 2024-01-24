package dev.trifanya.tms_server.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.dto.UserDTO;
import dev.trifanya.tms_server.dto.SignUpRequest;
import dev.trifanya.tms_server.service.UserService;
import dev.trifanya.tms_server.util.MainClassConverter;
import dev.trifanya.tms_server.validator.UserValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final MainClassConverter converter;
    private final UserValidator userValidator;

    @GetMapping("/profile")
    public UserDTO getUserProfile() {
        User user = userService.getUser(1);
        return converter.convertToUserDTO(user);
    }

    @GetMapping("/{userEmail}/profile")
    public UserDTO getUserProfile(@PathVariable("userEmail") String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        return converter.convertToUserDTO(user);
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(converter::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid SignUpRequest request) {
        userValidator.validateNewUser(request.getUserDTO().getEmail());
        userService.createNewUser(converter.convertToUser(request.getUserDTO()));
        return ResponseEntity.ok("Регистрация прошла успешно.");
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody @Valid UserDTO userDTO) {
        User user = userService.getUser(1);
        userValidator.validateUpdatedUser(user.getId(), userDTO.getEmail());
        userService.updateUserInfo(user.getId(), converter.convertToUser(userDTO));
        return ResponseEntity.ok("Данные успешно отредактированы.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        User user = userService.getUser(1);
        userService.deleteUser(user.getId());
        return ResponseEntity.ok("Пользователь успешно удален.");
    }
}
