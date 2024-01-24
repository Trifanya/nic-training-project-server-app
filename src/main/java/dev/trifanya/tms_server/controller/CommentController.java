package dev.trifanya.tms_server.controller;

import javax.validation.Valid;

import dev.trifanya.tms_server.service.UserService;
import lombok.RequiredArgsConstructor;
import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.dto.CommentDTO;
import dev.trifanya.tms_server.model.task.Task;
import dev.trifanya.tms_server.service.TaskService;
import dev.trifanya.tms_server.service.CommentService;
import dev.trifanya.tms_server.util.MainClassConverter;
import dev.trifanya.tms_server.validator.CommentValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final TaskService taskService;
    private final UserService userService;
    private final CommentService commentService;
    private final MainClassConverter converter;
    private final CommentValidator commentValidator;

    @GetMapping("/{taskId}")
    public List<CommentDTO> getComments(@PathVariable("taskId") int taskId) {
        return commentService.getComments(taskId)
                .stream()
                .map(converter::convertToCommentDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/new/{taskId}")
    public ResponseEntity<?> createNewComment(@RequestBody @Valid CommentDTO commentDTO) {
        User user = userService.getUser(1);
        System.out.println(user.getEmail());

        Task task = taskService.getTask(commentDTO.getTaskId());
        //System.out.println(task.getId() + " " + task.getTitle());
        commentValidator.validateNewComment(task, user);
        commentService.createNewComment(converter.convertToComment(commentDTO), task, user);
        return ResponseEntity.ok("Комментарий успешно добавлен.");
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateCommentInfo(@RequestBody @Valid CommentDTO commentDTO) {
        User user = userService.getUser(1);

        commentValidator.validateUpdatedComment(
                commentService.getComment(commentDTO.getId()).getAuthor(),
                user
        );
        commentService.updateCommentInfo(converter.convertToComment(commentDTO));
        return ResponseEntity.ok("Комментарий успешно отредактирован.");
    }

    @DeleteMapping("{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId) {
        User user = userService.getUser(1);

        commentValidator.validateDelete(commentService.getComment(commentId).getAuthor(), user);
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Комментарий успешно удален.");
    }
}
