package dev.trifanya.spring_webapp.controller;

import dev.trifanya.spring_webapp.model.task.Task;
import dev.trifanya.spring_webapp.service.TaskService;

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
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    @GetMapping("/task_{taskId}")
    public String getTask(@PathVariable("taskId") int taskId, Model model) throws JMSException, JsonProcessingException {
        logger.trace("Вызван метод getTask().");
        Task task = taskService.getTask(taskId);
        model.addAttribute("task", task);
        return "task/taskInfo";
    }

    @GetMapping("/list")
    public String getTaskList(@RequestParam Map<String, String> requestParams, Model model) throws JMSException, JsonProcessingException {
        logger.trace("Вызван метод getTaskList().");
        List<Task> taskList = taskService.getTasks(requestParams);
        model.addAttribute("taskList", taskList);
        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            model.addAttribute(param.getKey(), param.getValue());
        }
        return "task/taskList";
    }
}
