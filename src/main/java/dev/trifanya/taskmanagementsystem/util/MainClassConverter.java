package dev.trifanya.taskmanagementsystem.util;

import dev.trifanya.taskmanagementsystem.dto.TaskDTO;
import dev.trifanya.taskmanagementsystem.dto.UserDTO;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainClassConverter {
    private final ModelMapper modelMapper;

    public Task convertToTask(TaskDTO dto) {
        return modelMapper.map(dto, Task.class);
    }

    public TaskDTO convertToTaskDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    public User convertToUser(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
