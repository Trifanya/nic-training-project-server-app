package dev.trifanya.spring_webapp.controller;

import dev.trifanya.spring_webapp.model.User;
import dev.trifanya.spring_webapp.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;
import java.util.List;
import javax.jms.JMSException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/user_{userId}")
    public String getUser(@PathVariable("userId") int userId, Model model) throws JMSException, JsonProcessingException {
        logger.trace("Вызван метод getUser().");
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "user/userInfo";
    }

    @GetMapping("/list")
    public String getUserList(@RequestParam Map<String, String> requestParams, Model model) throws JMSException, JsonProcessingException {
        logger.trace("Вызван метод getUserList().");
        List<User> userList = userService.getUsers(requestParams);
        model.addAttribute("userList", userList);
        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            model.addAttribute(param.getKey(), param.getValue());
        }
        return "user/userList";
    }
}
