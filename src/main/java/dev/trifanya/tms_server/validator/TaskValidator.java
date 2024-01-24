package dev.trifanya.tms_server.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.dto.TaskDTO;
import dev.trifanya.tms_server.model.task.Task;
import dev.trifanya.tms_server.service.TaskService;
import dev.trifanya.tms_server.service.UserService;
import dev.trifanya.tms_server.exception.InvalidDataException;
import dev.trifanya.tms_server.exception.UnavailableActionException;

@Component
@RequiredArgsConstructor
public class TaskValidator {
    private final TaskService taskService;
    private final UserService userService;

    public void validateNewTask(TaskDTO taskDTO, User author) {
        User performer = userService.getUserByEmail(taskDTO.getPerformerEmail());

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
