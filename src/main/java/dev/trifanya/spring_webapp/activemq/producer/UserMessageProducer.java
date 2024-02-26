package dev.trifanya.spring_webapp.activemq.producer;

import dev.trifanya.spring_webapp.SpringWebApp;
import dev.trifanya.spring_webapp.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jms.core.MessageCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.List;
import javax.jms.Session;
import java.util.HashMap;
import java.util.ArrayList;
import javax.jms.TextMessage;
import javax.jms.JMSException;

@Component
public class UserMessageProducer {
    private static final Logger logger = SpringWebApp.logger;
    @Value("${spring.activemq.web-to-crud-queue}")
    private String destinationName;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final String REQUEST_CORRELATION_ID = "rqstCrlID";

    @Autowired
    public UserMessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public User sendGetUserMessage(int userId) throws JMSException, JsonProcessingException {
        logger.trace("UserMessageProducer: Вызван метод sendGetUserMessage()");
        TextMessage response = (TextMessage) jmsTemplate.sendAndReceive(destinationName, new MessageCreator() {
            @Override
            public TextMessage createMessage(Session session) throws JMSException {
                TextMessage textMessage = null;
                try {
                    textMessage = session.createTextMessage(objectMapper.writeValueAsString(userId));
                    textMessage.setStringProperty("Request name", "Get single user");
                    textMessage.setJMSCorrelationID(REQUEST_CORRELATION_ID);
                } catch (JsonProcessingException e) {
                    logger.error("UserMessageProducer: Произошла ошибка при попытке сформировать сообщение.");
                }
                return textMessage;
            }
        });
        logger.trace("UserMessageProducer: Сообщение успешно отправлено, ответ получен.");
        return objectMapper.readValue(response.getText(), User.class);
    }

    public List<User> sendGetUserListMessage(Map<String, String> requestParams) throws JMSException, JsonProcessingException {
        logger.trace("UserMessageProducer: Вызван метод sendGetUserListMessage()");
        Map<String, String> notNullParams = new HashMap<>();
        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            if (!param.getValue().equals("")) {
                notNullParams.put(param.getKey(), param.getValue());
            }
        }
        for (Map.Entry<String, String> param : notNullParams.entrySet()) {
            System.out.println("key: " + param.getKey() + " value: " + param.getValue());
        }
        TextMessage response = (TextMessage) jmsTemplate.sendAndReceive(destinationName, new MessageCreator() {
            @Override
            public TextMessage createMessage(Session session) throws JMSException {
                TextMessage textMessage = null;
                try {
                    textMessage = session.createTextMessage(objectMapper.writeValueAsString(notNullParams));
                    textMessage.setStringProperty("Request name", "Get user list");
                    textMessage.setJMSCorrelationID(REQUEST_CORRELATION_ID);
                } catch (JsonProcessingException e) {
                    logger.error("UserMessageProducer: Произошла ошибка при попытке сформировать сообщение.");
                }
                return textMessage;
            }
        });
        logger.trace("UserMessageProducer: Сообщение успешно отправлено, ответ получен.");
        return objectMapper.readValue(response.getText(), new TypeReference<ArrayList<User>>(){});
    }
}
