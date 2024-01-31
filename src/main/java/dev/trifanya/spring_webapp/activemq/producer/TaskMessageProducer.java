package dev.trifanya.spring_webapp.activemq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.spring_webapp.model.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TaskMessageProducer {
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
        TextMessage response = (TextMessage) jmsTemplate.sendAndReceive(destinationName, new MessageCreator() {
            @Override
            public TextMessage createMessage(Session session) throws JMSException {
                TextMessage textMessage = null;
                try {
                    textMessage = session.createTextMessage(objectMapper.writeValueAsString(taskId));
                    textMessage.setStringProperty("Request name", "Get single task");
                    textMessage.setJMSCorrelationID(REQUEST_CORRELATION_ID);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return textMessage;
            }
        });
        return objectMapper.readValue(response.getText(), Task.class);
    }

    public List<Task> sendGetTaskListMessage(Map<String, String> requestParams) throws JMSException, JsonProcessingException{
        TextMessage response = (TextMessage) jmsTemplate.sendAndReceive(destinationName, new MessageCreator() {
            @Override
            public TextMessage createMessage(Session session) throws JMSException {
                TextMessage textMessage = null;
                try {
                    textMessage = session.createTextMessage(objectMapper.writeValueAsString(requestParams));
                    textMessage.setStringProperty("Request name", "Get task list");
                    textMessage.setJMSCorrelationID(REQUEST_CORRELATION_ID);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return textMessage;
            }
        });
        return objectMapper.readValue(response.getText(), new TypeReference<ArrayList<Task>>(){});
    }
}

