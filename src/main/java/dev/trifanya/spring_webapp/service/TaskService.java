package dev.trifanya.tms_server.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.model.task.Task;
import dev.trifanya.tms_server.exception.NotFoundException;

import java.util.Map;
import java.util.List;
import java.time.LocalDateTime;

@Data
@Service
public class TaskService {
    //private final TaskSpecificationConstructor constructor;

    public Task getTask(int taskId) {
        return taskMapper.findTaskById(taskId)
                .orElseThrow(() -> new NotFoundException("Задача с указанным id не найдена."));
    }

    public List<Task> getAllTasks() {
        return taskMapper.findAllTasks();
    }

    /*public List<Task> getTasksByAuthor(User author, Map<String, String> filters) {
        PageRequest pageRequest = fetchPageRequest(filters);
        return taskMapper.findAll(
                constructor.createTaskSpecification("author", author, filters),
                pageRequest
        ).getContent();
    }*/

    /*public List<Task> getTasksByPerformer(User performer, Map<String, String> filters) {
        PageRequest pageRequest = fetchPageRequest(filters);
        return taskMapper.findAll(
                constructor.createTaskSpecification("performer", performer, filters),
                pageRequest
        ).getContent();
    }*/

    public List<Task> getTasksByAuthor(User author, Map<String, String> filters) {
        return taskMapper.findAllTasksByAuthorId(author.getId());
    }

    public List<Task> getTasksByPerformer(User performer, Map<String, String> filters) {
        return taskMapper.findAllTasksByPerformerId(performer.getId());
    }


    public void createNewTask(User author, User performer, Task taskToSave) {
        taskMapper.saveTask(taskToSave
                .setCreatedAt(LocalDateTime.now())
                .setAuthor(author)
                .setPerformer(performer)
        );
    }

    public void updateTaskInfo(Task updatedTask) {
        Task taskToUpdate = getTask(updatedTask.getId());
        taskMapper.updateTask(updatedTask
                .setCreatedAt(taskToUpdate.getCreatedAt())
                .setAuthor(taskToUpdate.getAuthor())
                .setPerformer(taskToUpdate.getPerformer())
        );
    }

    public void deleteTask(int taskToDeleteId) {
        taskMapper.deleteTaskById(taskToDeleteId);
    }

    /*private PageRequest fetchPageRequest(Map<String, String> filters) {
        int pageNumber = Integer.parseInt(
                filters.get("pageNumber") == null ? "0" : filters.remove("pageNumber"));
        int itemsPerPage = Integer.parseInt(
                filters.get("itemsPerPage") == null ? "10" : filters.remove("itemsPerPage"));
        Sort.Direction sortDir = Sort.Direction.valueOf(
                filters.get("sortDir") == null ? "ASC" : filters.remove("sortDir"));
        String sortBy = filters.get("sortBy") == null ? "id" : filters.remove("sortBy");

        return PageRequest.of(pageNumber, itemsPerPage, sortDir, sortBy);
    }*/
}
