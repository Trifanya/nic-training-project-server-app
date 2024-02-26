package dev.trifanya.spring_webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.trifanya.spring_webapp.SpringWebApp;
import dev.trifanya.spring_webapp.model.task.Task;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import dev.trifanya.spring_webapp.activemq.producer.TaskMessageProducer;

import javax.jms.JMSException;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = SpringWebApp.logger;
    private final TaskMessageProducer taskMessageProducer;

    @GetMapping("/task_{taskId}")
    public String getTask(@PathVariable("taskId") int taskId, Model model) throws JMSException, JsonProcessingException {
        logger.trace("TaskController: Вызван метод getTask().");
        Task task = taskMessageProducer.sendGetTaskMessage(taskId);
        model.addAttribute("task", task);
        return "task/taskInfo";
    }

    @GetMapping("/list")
    public String getTaskList(@RequestParam Map<String, String> requestParams, Model model) throws JMSException, JsonProcessingException {
        logger.trace("TaskController: Вызван метод getTaskList().");
        List<Task> taskList = taskMessageProducer.sendGetTaskListMessage(requestParams);
        model.addAttribute("taskList", taskList);
        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            model.addAttribute(param.getKey(), param.getValue());
        }
        return "task/taskList";
    }
}
