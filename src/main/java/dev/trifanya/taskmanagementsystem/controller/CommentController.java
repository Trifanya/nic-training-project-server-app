package dev.trifanya.taskmanagementsystem.controller;

import dev.trifanya.taskmanagementsystem.dto.CommentDTO;
import dev.trifanya.taskmanagementsystem.service.CommentService;
import dev.trifanya.taskmanagementsystem.util.MainClassConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final MainClassConverter converter;

    @PostMapping("/{taskId}")
    public ResponseEntity<?> createNewComment(@RequestBody CommentDTO commentDTO) {
        commentService.createNewComment(converter.convertToComment(commentDTO));
        return ResponseEntity.ok("Комментарий успешно добавлен.");
    }

    @PatchMapping("/{commentId}/update")
    public ResponseEntity<?> updateCommentInfo(@RequestBody CommentDTO commentDTO) {
        commentService.updateCommentInfo(converter.convertToComment(commentDTO));
        return ResponseEntity.ok("Комментарий успешно отредактирован.");
    }

    @DeleteMapping("{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Комментарий успешно удален.");
    }
}
