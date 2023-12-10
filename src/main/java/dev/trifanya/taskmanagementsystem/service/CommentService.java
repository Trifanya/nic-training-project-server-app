package dev.trifanya.taskmanagementsystem.service;

import dev.trifanya.taskmanagementsystem.exception.AlreadyExistException;
import dev.trifanya.taskmanagementsystem.model.Comment;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import dev.trifanya.taskmanagementsystem.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment getComment(int commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new AlreadyExistException("Комментарий с указанным id не найден."));
    }

    public List<Comment> getComments(int taskId) {
        return commentRepository.findAllByTaskId(taskId);
    }

    public void createNewComment(Comment comment, Task task, User author) {
        comment.setCreatedAt(LocalDateTime.now());
        comment.setTask(task);
        comment.setAuthor(author);

        commentRepository.save(comment);
    }

    public void updateCommentInfo(Comment updatedComment) {
        Comment commentToUpdate = getComment(updatedComment.getId());
        updatedComment.setCreatedAt(commentToUpdate.getCreatedAt());
        updatedComment.setTask(commentToUpdate.getTask());
        updatedComment.setAuthor(commentToUpdate.getAuthor());

        commentRepository.save(updatedComment);
    }

    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }
}
