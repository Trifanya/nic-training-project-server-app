package dev.trifanya.spring_webapp.controller;

import dev.trifanya.spring_webapp.activemq.producer.UserMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserMessageProducer userMessageProducer;

    @GetMapping("/user_{userId}")
    public ResponseEntity<String> getUser(@PathVariable("userId") int userId) {
        userMessageProducer.sendGetUserMessage();
        return ResponseEntity.ok("Отправлен запрос на получение пользователя.");
    }

    @GetMapping("/list")
    public ResponseEntity<String> getUserList(@RequestParam Map<String, String> filters) {
        userMessageProducer.sendGetUserListMessage();
        return ResponseEntity.ok("Отправлен запрос на получение списка пользователей.");
    }
}
