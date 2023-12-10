package dev.trifanya.taskmanagementsystem.controller;

import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.service.UserService;
import dev.trifanya.taskmanagementsystem.validator.TaskValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import dev.trifanya.taskmanagementsystem.dto.TaskDTO;
import dev.trifanya.taskmanagementsystem.service.TaskService;
import dev.trifanya.taskmanagementsystem.util.MainClassConverter;

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

    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable("id") int taskId) {
        return converter.convertToTaskDTO(taskService.getTask(taskId));
    }

    @GetMapping("/{authorId}")
    public List<TaskDTO> getCreatedTasks(@PathVariable("authorId") int authorId) {
        return taskService.getCreatedTasks(authorId)
                .stream()
                .map(converter::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{performerId}")
    public List<TaskDTO> getAssignedTasks(@PathVariable("performerId") int performerId) {
        return taskService.getAssignedTasks(performerId)
                .stream()
                .map(converter::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewTask(@RequestBody @Valid TaskDTO taskDTO,
                                           @AuthenticationPrincipal User author) {
        taskValidator.performNewTaskValidation(taskDTO, author);
        taskService.createNewTask(
                author,
                userService.getUser(taskDTO.getPerformerId()),
                converter.convertToTask(taskDTO)
        );
        return ResponseEntity.ok("Задача успешно добавлена в список задач.");
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<?> updateTaskInfo(@RequestBody @Valid TaskDTO taskDTO,
                                            @AuthenticationPrincipal User modifier) {
        taskValidator.performUpdatedTaskValidation(taskDTO, modifier);
        taskService.updateTaskInfo(converter.convertToTask(taskDTO));
        return ResponseEntity.ok("Задача успешно обновлена.");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") int taskToDeleteId) {
        taskService.deleteTask(taskToDeleteId);
        return ResponseEntity.ok("Задача успешно удалена.");
    }
}
