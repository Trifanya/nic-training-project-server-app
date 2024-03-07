package dev.trifanya.spring_webapp.activemq.producer;

import dev.trifanya.spring_webapp.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jms.core.MessageCreator;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.List;
import javax.jms.Session;
import java.util.ArrayList;
import javax.jms.TextMessage;
import javax.jms.JMSException;

@Component
@RequiredArgsConstructor
public class UserMessageProducer {
    private static final Logger logger = LoggerFactory.getLogger(UserMessageProducer.class);

    @Value("${spring.activemq.web-to-crud-queue}")
    private String destinationName;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public User sendGetUserMessage(int userId) throws JMSException, JsonProcessingException {
        logger.trace("Вызван метод sendGetUserMessage()");
        TextMessage response = (TextMessage) jmsTemplate.sendAndReceive(destinationName, new MessageCreator() {
            @Override
            public TextMessage createMessage(Session session) throws JMSException {
                TextMessage textMessage = null;
                try {
                    textMessage = session.createTextMessage(objectMapper.writeValueAsString(userId));
                    textMessage.setStringProperty("Request name", "Get single user");
                } catch (JsonProcessingException e) {
                    logger.error("Произошла ошибка при попытке сформировать сообщение.");
                }
                return textMessage;
            }
        });
        logger.trace("Сообщение успешно отправлено, ответ получен.");
        return objectMapper.readValue(response.getText(), User.class);
    }

    public List<User> sendGetUserListMessage(Map<String, String> requestParams) throws JMSException, JsonProcessingException {
        logger.trace("Вызван метод sendGetUserListMessage()");
        TextMessage response = (TextMessage) jmsTemplate.sendAndReceive(destinationName, new MessageCreator() {
            @Override
            public TextMessage createMessage(Session session) throws JMSException {
                TextMessage textMessage = null;
                try {
                    textMessage = session.createTextMessage(objectMapper.writeValueAsString(requestParams));
                    textMessage.setStringProperty("Request name", "Get user list");
                } catch (JsonProcessingException e) {
                    logger.error("Произошла ошибка при попытке сформировать сообщение.");
                }
                return textMessage;
            }
        });
        logger.trace("Сообщение успешно отправлено, ответ получен.");
        return objectMapper.readValue(response.getText(), new TypeReference<ArrayList<User>>(){});
    }
}
