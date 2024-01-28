package dev.trifanya.spring_webapp.activemq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trifanya.spring_webapp.activemq.request.GetTaskListRequest;
import dev.trifanya.spring_webapp.activemq.request.GetTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Map;

@Component
public class TaskMessageProducer {
    @Value("${spring.activemq.web-to-crud-queue}")
    private String destinationName;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public TaskMessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = new ObjectMapper();
    }


    public void sendGetTaskMessage(int taskId) throws JMSException {
        GetTaskRequest getTaskRequest = new GetTaskRequest()
                .setRequestTitle("Get single task")
                .setTaskId(taskId);
        getTaskRequest.setTaskId(taskId);

        jmsTemplate.send(destinationName, new MessageCreator() {
            @Override
            public TextMessage createMessage(Session session) throws JMSException {
                TextMessage textMessage = null;
                try {
                    textMessage = session.createTextMessage(objectMapper.writeValueAsString(getTaskRequest));
                    textMessage.setStringProperty("Request name", "Get single task");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return textMessage;
            }
        });
    }

    public void sendGetTaskListMessage(Map<String, String> filters, String sortBy, String sortDir) {
        GetTaskListRequest getTaskListRequest = new GetTaskListRequest()
                .setRequestTitle("Get task list")
                .setFilters(filters)
                .setSortBy(sortBy)
                .setSortDir(sortDir);

        jmsTemplate.send(destinationName, new MessageCreator() {
            @Override
            public TextMessage createMessage(Session session) throws JMSException {
                TextMessage textMessage = null;
                try {
                    textMessage = session.createTextMessage(objectMapper.writeValueAsString(getTaskListRequest));
                    textMessage.setStringProperty("Request name", "Get task list");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return textMessage;
            }
        });
        System.out.println("Request sent");
    }
}

