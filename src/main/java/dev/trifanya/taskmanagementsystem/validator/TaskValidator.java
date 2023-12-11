package dev.trifanya.taskmanagementsystem.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.dto.TaskDTO;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import dev.trifanya.taskmanagementsystem.service.TaskService;
import dev.trifanya.taskmanagementsystem.service.UserService;
import dev.trifanya.taskmanagementsystem.exception.InvalidDataException;
import dev.trifanya.taskmanagementsystem.exception.UnavailableActionException;

@Component
@RequiredArgsConstructor
public class TaskValidator {
    private final TaskService taskService;
    private final UserService userService;

    public void validateNewTask(TaskDTO taskDTO, User author) {
        User performer = userService.getUser(taskDTO.getPerformerId());

        if (author.equals(performer)) {
            throw new InvalidDataException("Исполнителем задачи не может быть ее автор.");
        }
    }

    public void validateUpdatedTask(TaskDTO taskDTO, User modifier) {
        Task taskToUpdate = taskService.getTask(taskDTO.getId());

        // Если кто-то, кто не является автором задачи, пытается изменить ее описание,
        // приоритет или дедлайн, то будет выброшено исключение.
        if ((!taskDTO.getDescription().equals(taskToUpdate.getDescription()) ||
                !taskDTO.getPriority().equals(taskToUpdate.getPriority()) ||
                !taskDTO.getDeadline().equals(taskToUpdate.getDeadline())) &&
                !modifier.equals(taskToUpdate.getAuthor())) {
            throw new UnavailableActionException("Заголовок, описание, приоритет и дедлайн задачи может изменить только ее автор.");
        }

        // Если кто-то, кто не является исполнителем задачи, пытается изменить ее
        // статус, то будет выброшено исключение.
        if (!taskDTO.getStatus().equals(taskToUpdate.getStatus()) &&
                !modifier.equals(taskToUpdate.getPerformer())) {
            throw new UnavailableActionException("Статус задачи может изменить только ее исполнитель.");
        }
    }

    public void validateDelete(User author, User deleter) {
        if (!author.equals(deleter)) {
            throw new UnavailableActionException("Удалить задачу может только ее автор.");
        }
    }
}
