package dev.trifanya.taskmanagementsystem.service;

import dev.trifanya.taskmanagementsystem.exception.NotFoundException;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import dev.trifanya.taskmanagementsystem.repository.TaskRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    public Task getTask(int taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Задача с указанным id не найдена."));
    }

    public List<Task> getCreatedTasks(int authorId) {
        return taskRepository.getAllByAuthorId(authorId);
    }

    public List<Task> getAssignedTasks(int performerId) {
        return taskRepository.getAllByPerformerId(performerId);
    }

    public void createNewTask(Task taskToSave) {
        //taskToSave.setTimestamp(LocalDateTime.now());
        taskRepository.save(taskToSave);
    }

    public void updateTaskInfo(Task updatedTask) {
        taskRepository.save(updatedTask);
    }

    public void deleteTask(int taskToDeleteId) {
        taskRepository.deleteById(taskToDeleteId);
    }
}
