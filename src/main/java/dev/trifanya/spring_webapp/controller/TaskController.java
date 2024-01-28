package dev.trifanya.spring_webapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.trifanya.spring_webapp.activemq.producer.TaskMessageProducer;

import javax.jms.JMSException;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskMessageProducer taskMessageProducer;

    @GetMapping("/task_{taskId}")
    public ResponseEntity<String> getTask(@PathVariable("taskId") int taskId) throws JMSException {
        taskMessageProducer.sendGetTaskMessage(taskId);
        return ResponseEntity.ok("Отправлен запрос на получение задачи.");
    }

    @GetMapping("/list")
    public ResponseEntity<String> getTaskList(@RequestParam Map<String, String> filters,
                                              @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                              @RequestParam(name = "sortDir", defaultValue = "ASC") String sortDir) {
        taskMessageProducer.sendGetTaskListMessage(filters, sortBy, sortDir);
        return ResponseEntity.ok("Отправлен запрос на получение списка задач.");
    }
}
