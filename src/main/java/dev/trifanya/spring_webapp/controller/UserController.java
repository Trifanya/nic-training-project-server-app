package dev.trifanya.spring_webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.trifanya.spring_webapp.activemq.producer.UserMessageProducer;
import dev.trifanya.spring_webapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserMessageProducer userMessageProducer;

    @GetMapping("/user_{userId}")
    public String getUser(@PathVariable("userId") int userId, Model model) throws JMSException, JsonProcessingException {
        User user = userMessageProducer.sendGetUserMessage(userId);
        model.addAttribute("user", user);
        return "/user/userInfo";
    }

    @GetMapping("/list")
    public String getUserList(@RequestParam Map<String, String> requestParams, Model model) throws JMSException, JsonProcessingException {
        List<User> userList = userMessageProducer.sendGetUserListMessage(requestParams);
        model.addAttribute("userList", userList);
        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            model.addAttribute(param.getKey(), param.getValue());
        }
        return "/user/userList";
    }
}
