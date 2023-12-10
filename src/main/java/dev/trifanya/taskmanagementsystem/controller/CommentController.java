package dev.trifanya.taskmanagementsystem.controller;

import dev.trifanya.taskmanagementsystem.dto.CommentDTO;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import dev.trifanya.taskmanagementsystem.service.CommentService;
import dev.trifanya.taskmanagementsystem.service.TaskService;
import dev.trifanya.taskmanagementsystem.util.MainClassConverter;
import dev.trifanya.taskmanagementsystem.validator.CommentValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final TaskService taskService;
    private final CommentService commentService;
    private final MainClassConverter converter;
    private final CommentValidator commentValidator;

    @PostMapping("/{taskId}")
    public ResponseEntity<?> createNewComment(@RequestBody @Valid CommentDTO commentDTO,
                                              @AuthenticationPrincipal User currentUser) {
        Task task = taskService.getTask(commentDTO.getTaskId());
        commentValidator.validateNewComment(task, currentUser);
        commentService.createNewComment(converter.convertToComment(commentDTO), task, currentUser);
        return ResponseEntity.ok("Комментарий успешно добавлен.");
    }

    @PatchMapping("/{commentId}/update")
    public ResponseEntity<?> updateCommentInfo(@RequestBody @Valid CommentDTO commentDTO,
                                               @AuthenticationPrincipal User currentUser) {
        commentValidator.validateUpdatedComment(
                commentService.getComment(commentDTO.getId()).getAuthor(),
                currentUser
        );
        commentService.updateCommentInfo(converter.convertToComment(commentDTO));
        return ResponseEntity.ok("Комментарий успешно отредактирован.");
    }

    @DeleteMapping("{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Комментарий успешно удален.");
    }
}
