package dev.trifanya.spring_webapp.util;

import dev.trifanya.spring_webapp.dto.TaskDTO;
import dev.trifanya.spring_webapp.model.User;
import dev.trifanya.spring_webapp.model.task.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import dev.trifanya.tms_server.dto.UserDTO;

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
