package dev.trifanya.spring_webapp.service;

import dev.trifanya.spring_webapp.model.User;
import dev.trifanya.spring_webapp.activemq.producer.UserMessageProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import javax.jms.JMSException;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private final UserMessageProducer userMessageProducer;

    public User getUser(int userId) throws JMSException, JsonProcessingException {
        logger.trace("Вызван метод getUser().");
        return userMessageProducer.sendGetUserMessage(userId);
    }

    public List<User> getUsers(Map<String, String> requestParams) throws JMSException, JsonProcessingException {
        logger.trace("Вызван метод getUsers().");
        Map<String, String> notNullParams = new HashMap<>();
        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            if (!param.getValue().equals("")) {
                notNullParams.put(param.getKey(), param.getValue());
            }
        }
        logger.info("Извлеченные параметры запроса: " + notNullParams.entrySet());
        return userMessageProducer.sendGetUserListMessage(notNullParams);
    }
}
