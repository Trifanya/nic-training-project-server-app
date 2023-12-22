package dev.trifanya.taskmanagementsystem.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.dto.TaskDTO;
import dev.trifanya.taskmanagementsystem.service.UserService;
import dev.trifanya.taskmanagementsystem.service.TaskService;
import dev.trifanya.taskmanagementsystem.util.MainClassConverter;
import dev.trifanya.taskmanagementsystem.validator.TaskValidator;

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
    public ResponseEntity<?> createNewTask(@RequestBody @Valid TaskDTO taskDTO,
                                           @AuthenticationPrincipal User author) {
        taskValidator.validateNewTask(taskDTO, author);
        taskService.createNewTask(
                author,
                userService.getUser(taskDTO.getPerformerId()),
                converter.convertToTask(taskDTO)
        );
        return ResponseEntity.ok("Задача успешно добавлена в список задач.");
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateTaskInfo(@RequestBody @Valid TaskDTO taskDTO,
                                            @AuthenticationPrincipal User modifier) {
        taskValidator.validateUpdatedTask(taskDTO, modifier);
        taskService.updateTaskInfo(converter.convertToTask(taskDTO));
        return ResponseEntity.ok("Задача успешно обновлена.");
    }


    @DeleteMapping("/{taskId}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") int taskToDeleteId,
                                        @AuthenticationPrincipal User currentUser) {
        taskValidator.validateDelete(taskService.getTask(taskToDeleteId).getAuthor(), currentUser);
        taskService.deleteTask(taskToDeleteId);
        return ResponseEntity.ok("Задача успешно удалена.");
    }
}
