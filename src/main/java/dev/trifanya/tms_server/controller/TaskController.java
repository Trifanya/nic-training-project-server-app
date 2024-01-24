package dev.trifanya.tms_server.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.dto.TaskDTO;
import dev.trifanya.tms_server.service.UserService;
import dev.trifanya.tms_server.service.TaskService;
import dev.trifanya.tms_server.util.MainClassConverter;
import dev.trifanya.tms_server.validator.TaskValidator;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final TaskValidator taskValidator;
    private final MainClassConverter converter;


    @GetMapping("/task_id_{taskId}")
    public TaskDTO getTask(@PathVariable("taskId") int taskId) {
        return converter.convertToTaskDTO(taskService.getTask(taskId));
    }

    @GetMapping("/all")
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks()
                .stream()
                .map(converter::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/author_id_{authorId}")
    public List<TaskDTO> getTasksByAuthor(@PathVariable("authorId") int authorId,
                                          @RequestParam Map<String, String> allParams) {
        return taskService.getTasksByAuthor(userService.getUser(authorId), allParams)
                .stream()
                .map(converter::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/performer_id_{performerId}")
    public List<TaskDTO> getTasksByPerformer(@PathVariable("performerId") int performerId,
                                             @RequestParam Map<String, String> allParams) {
        return taskService.getTasksByPerformer(userService.getUser(performerId), allParams)
                .stream()
                .map(converter::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewTask(@RequestBody @Valid TaskDTO taskDTO) {
        User user = userService.getUser(1);
        taskValidator.validateNewTask(taskDTO, user);
        taskService.createNewTask(
                user,
                userService.getUserByEmail(taskDTO.getPerformerEmail()),
                converter.convertToTask(taskDTO)
        );
        return ResponseEntity.ok("Задача успешно добавлена в список задач.");
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateTaskInfo(@RequestBody @Valid TaskDTO taskDTO) {
        User user = userService.getUser(1);
        taskValidator.validateUpdatedTask(taskDTO, user);
        taskService.updateTaskInfo(converter.convertToTask(taskDTO));
        return ResponseEntity.ok("Задача успешно обновлена.");
    }


    @DeleteMapping("/{taskId}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") int taskToDeleteId) {
        User user = userService.getUser(1);
        taskValidator.validateDelete(taskService.getTask(taskToDeleteId).getAuthor(), user);
        taskService.deleteTask(taskToDeleteId);
        return ResponseEntity.ok("Задача успешно удалена.");
    }
}
