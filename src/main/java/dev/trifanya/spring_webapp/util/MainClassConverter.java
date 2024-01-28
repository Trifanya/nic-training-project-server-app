package dev.trifanya.tms_server.util;

import dev.trifanya.spring_webapp.dto.TaskDTO;
import dev.trifanya.spring_webapp.model.User;
import dev.trifanya.spring_webapp.model.task.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import dev.trifanya.tms_server.dto.UserDTO;
import dev.trifanya.tms_server.model.Comment;
import dev.trifanya.tms_server.dto.CommentDTO;

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

    public Comment convertToComment(CommentDTO dto) {
        return modelMapper.map(dto, Comment.class);
    }

    public CommentDTO convertToCommentDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }
}
