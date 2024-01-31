package dev.trifanya.spring_webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.trifanya.spring_webapp.activemq.producer.UserMessageProducer;
import dev.trifanya.spring_webapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserMessageProducer userMessageProducer;

    @GetMapping("/user_{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") int userId) throws JMSException, JsonProcessingException {
        User user = userMessageProducer.sendGetUserMessage(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getUserList(@RequestParam Map<String, String> requestParams) throws JMSException, JsonProcessingException {
        List<User> userList = userMessageProducer.sendGetUserListMessage(requestParams);
        return ResponseEntity.ok(userList);
    }
}
