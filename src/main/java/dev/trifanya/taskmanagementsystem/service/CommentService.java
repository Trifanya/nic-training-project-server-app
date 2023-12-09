package dev.trifanya.taskmanagementsystem.service;

import dev.trifanya.taskmanagementsystem.model.Comment;
import dev.trifanya.taskmanagementsystem.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getComments(int taskId) {
        return commentRepository.findAllByTaskId(taskId);
    }

    public void createNewComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public void updateCommentInfo(Comment updatedComment) {
        commentRepository.save(updatedComment);
    }

    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }
}
