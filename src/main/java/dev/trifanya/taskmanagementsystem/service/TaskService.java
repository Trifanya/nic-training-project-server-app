package dev.trifanya.taskmanagementsystem.service;

import dev.trifanya.taskmanagementsystem.exception.NotFoundException;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import dev.trifanya.taskmanagementsystem.repository.TaskRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    public Task getTask(int taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Задача с указанным id не найдена."));
    }

    public void createNewTask(Task taskToSave) {
        taskRepository.save(taskToSave);
    }

    public void updateTaskInfo(int taskToUpdateId, Task updatedTask) {
        updatedTask.setId(taskToUpdateId);
        taskRepository.save(updatedTask);
    }

    public void deleteTask(int taskToDeleteId) {
        taskRepository.deleteById(taskToDeleteId);
    }
}
