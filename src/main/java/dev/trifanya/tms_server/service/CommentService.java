package dev.trifanya.tms_server.service;

import dev.trifanya.tms_server.mybatis.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.model.Comment;
import dev.trifanya.tms_server.model.task.Task;
import dev.trifanya.tms_server.exception.NotFoundException;

import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    public Comment getComment(int commentId) {
        return commentMapper.findCommentById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с указанным id не найден."));
    }

    public List<Comment> getComments(int taskId) {
        return commentMapper.findAllCommentsByTaskId(taskId);
    }

    public void createNewComment(Comment comment, Task task, User author) {
        commentMapper.saveComment(comment
                .setCreatedAt(LocalDateTime.now())
                .setTask(task)
                .setAuthor(author));
    }

    public void updateCommentInfo(Comment updatedComment) {
        Comment commentToUpdate = getComment(updatedComment.getId());
        commentMapper.updateComment(updatedComment
                .setCreatedAt(commentToUpdate.getCreatedAt())
                .setTask(commentToUpdate.getTask())
                .setAuthor(commentToUpdate.getAuthor()));
    }

    public void deleteComment(int commentId) {
        commentMapper.deleteCommentById(commentId);
    }
}
