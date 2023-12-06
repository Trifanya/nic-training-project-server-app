package dev.trifanya.taskmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.trifanya.taskmanagementsystem.dto.TaskDTO;
import dev.trifanya.taskmanagementsystem.service.TaskService;
import dev.trifanya.taskmanagementsystem.util.MainClassConverter;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    private final MainClassConverter converter;

    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable("id") int taskId) {
        return converter.convertToTaskDTO(taskService.getTask(taskId));
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewTask(@RequestBody TaskDTO taskDTO) {
        taskService.createNewTask(converter.convertToTask(taskDTO));

        return ResponseEntity.ok("Задача успешно добавлена в список задач.");
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<?> updateTaskInfo(@RequestBody TaskDTO taskDTO,
                                            @PathVariable("id") int taskToUpdateId) {
        taskService.updateTaskInfo(
                taskToUpdateId,
                converter.convertToTask(taskDTO)
        );

        return ResponseEntity.ok("Задача успешно обновлена.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") int taskToDeleteId) {
        taskService.deleteTask(taskToDeleteId);

        return ResponseEntity.ok("Задача успешно удалена.");
    }
}
