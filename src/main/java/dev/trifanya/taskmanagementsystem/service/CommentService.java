package dev.trifanya.taskmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.model.Comment;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import dev.trifanya.taskmanagementsystem.exception.NotFoundException;
import dev.trifanya.taskmanagementsystem.repository.CommentRepository;

import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment getComment(int commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с указанным id не найден."));
    }

    public List<Comment> getComments(int taskId) {
        return commentRepository.findAllByTaskId(taskId);
    }

    public Comment createNewComment(Comment comment, Task task, User author) {
        return commentRepository.save(comment
                .setCreatedAt(LocalDateTime.now())
                .setTask(task)
                .setAuthor(author));
    }

    public Comment updateCommentInfo(Comment updatedComment) {
        Comment commentToUpdate = getComment(updatedComment.getId());
        return commentRepository.save(updatedComment
                .setCreatedAt(commentToUpdate.getCreatedAt())
                .setTask(commentToUpdate.getTask())
                .setAuthor(commentToUpdate.getAuthor()));
    }

    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }
}
