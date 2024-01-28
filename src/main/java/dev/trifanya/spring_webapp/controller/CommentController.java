package dev.trifanya.spring_webapp.controller;

import dev.trifanya.spring_webapp.activemq.producer.CommentMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentMessageProducer commentMessageProducer;

    @GetMapping("/comment_{commentId}")
    public ResponseEntity<String> getComment(@PathVariable("commentId") int commentId) {
        commentMessageProducer.sendGetCommentMessage();
        return ResponseEntity.ok("Отправлен запрос на получение комментария.");
    }

    @GetMapping("/list")
    public ResponseEntity<String> getCommentList(@RequestParam Map<String, String> filters) {
        commentMessageProducer.sendGetCommentListMessage();
        return ResponseEntity.ok("Отправлен запрос на получение списка комментариев.");
    }
}
