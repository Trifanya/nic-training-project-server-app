package dev.trifanya.spring_webapp.service;

import dev.trifanya.spring_webapp.model.task.Task;
import dev.trifanya.spring_webapp.activemq.producer.TaskMessageProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import javax.jms.JMSException;

@Service
@RequiredArgsConstructor
public class TaskService {
    public static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    public final TaskMessageProducer taskMessageProducer;

    public Task getTask(int taskId) throws JMSException, JsonProcessingException {
        logger.trace("Вызван метод getTask().");
        return taskMessageProducer.sendGetTaskMessage(taskId);
    }

    public List<Task> getTasks(Map<String, String> requestParams) throws JMSException, JsonProcessingException {
        logger.trace("Вызван метод getTasks().");
        Map<String, String> notNullParams = new HashMap<>();
        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            if (!param.getValue().equals("")) {
                notNullParams.put(param.getKey(), param.getValue());
            }
        }
        logger.info("Извлеченные параметры запроса: " + notNullParams.entrySet());
        return taskMessageProducer.sendGetTaskListMessage(notNullParams);
    }
}
