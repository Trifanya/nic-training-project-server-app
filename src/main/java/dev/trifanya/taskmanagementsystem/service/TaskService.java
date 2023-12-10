package dev.trifanya.taskmanagementsystem.service;

import dev.trifanya.taskmanagementsystem.exception.NotFoundException;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import dev.trifanya.taskmanagementsystem.repository.TaskRepository;
import dev.trifanya.taskmanagementsystem.service.specification.TaskSpecificationConstructor;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskSpecificationConstructor constructor;

    public Task getTask(int taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Задача с указанным id не найдена."));
    }

    public List<Task> getTasksByAuthor(int authorId, Map<String, String> filters) {
        return taskRepository.findAll(
                constructor.createTaskSpecification("author_id", authorId, filters),
                fetchPageRequest(filters)
        ).getContent();
    }

    public List<Task> getTasksByPerformer(int performerId, Map<String, String> filters) {
        return taskRepository.findAll(
                constructor.createTaskSpecification("performer_id", performerId, filters),
                fetchPageRequest(filters)
        ).getContent();
    }

    public Task createNewTask(User author, User performer, Task taskToSave) {
        return taskRepository.save(taskToSave
                .setCreatedAt(LocalDateTime.now())
                .setAuthor(author)
                .setPerformer(performer)
        );
    }

    public Task updateTaskInfo(Task updatedTask) {
        Task taskToUpdate = getTask(updatedTask.getId());
        return taskRepository.save(updatedTask
                .setCreatedAt(taskToUpdate.getCreatedAt())
                .setAuthor(taskToUpdate.getAuthor())
                .setPerformer(taskToUpdate.getPerformer())
        );
    }

    public void deleteTask(int taskToDeleteId) {
        taskRepository.deleteById(taskToDeleteId);
    }

    private PageRequest fetchPageRequest(Map<String, String> filters) {
        int pageNumber = Integer.parseInt(filters.remove("pageNumber"));
        int itemsPerPage = Integer.parseInt(filters.remove("itemsPerPage"));
        Sort.Direction sortDir = Sort.Direction.valueOf(filters.remove("sortDir"));
        String sortBy = filters.remove("sortBy");

        return PageRequest.of(pageNumber, itemsPerPage, sortDir, sortBy);
    }
}
