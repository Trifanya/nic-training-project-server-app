package dev.trifanya.spring_webapp.activemq.producer;

import dev.trifanya.spring_webapp.SpringWebApp;
import dev.trifanya.spring_webapp.model.task.Task;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;
import java.util.List;
import javax.jms.Session;
import java.util.HashMap;
import java.util.ArrayList;
import javax.jms.TextMessage;
import javax.jms.JMSException;

@Component
public class TaskMessageProducer {
    private static final Logger logger = SpringWebApp.logger;
    @Value("${spring.activemq.web-to-crud-queue}")
    private String destinationName;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    private final String REQUEST_CORRELATION_ID = "rqstCrlID";

    @Autowired
    public TaskMessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public Task sendGetTaskMessage(int taskId) throws JMSException, JsonProcessingException {
        logger.trace("TaskMessageProducer: Вызван метод sendGetTaskMessage().");
        TextMessage response = (TextMessage) jmsTemplate.sendAndReceive(destinationName, new MessageCreator() {
            @Override
            public TextMessage createMessage(Session session) throws JMSException {
                TextMessage textMessage = null;
                try {
                    textMessage = session.createTextMessage(objectMapper.writeValueAsString(taskId));
                    textMessage.setStringProperty("Request name", "Get single task");
                } catch (JsonProcessingException e) {
                    logger.error("TaskMessageProducer: Произошла ошибка при попытке сформировать сообщение.");
                }
                return textMessage;
            }
        });
        logger.trace("TaskMessageProducer: Сообщение успешно отправлено, ответ получен.");
        return objectMapper.readValue(response.getText(), Task.class);
    }

    public List<Task> sendGetTaskListMessage(Map<String, String> requestParams) throws JMSException, JsonProcessingException {
        logger.trace("TaskMessageProducer: Вызван метод sendGetTaskListMessage().");
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
                    textMessage.setStringProperty("Request name", "Get task list");
                    //textMessage.setJMSCorrelationID(REQUEST_CORRELATION_ID);
                } catch (JsonProcessingException e) {
                    logger.error("TaskMessageProducer: Произошла ошибка при попытке сформировать сообщение.к");
                }
                return textMessage;
            }
        });
        logger.trace("TaskMessageProducer: Сообщение успешно отправлено, ответ получен.");
        return objectMapper.readValue(response.getText(), new TypeReference<ArrayList<Task>>(){});
    }
}

