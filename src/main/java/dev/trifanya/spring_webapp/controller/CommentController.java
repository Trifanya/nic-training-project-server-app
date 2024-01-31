package dev.trifanya.spring_webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.trifanya.spring_webapp.activemq.producer.CommentMessageProducer;
import dev.trifanya.spring_webapp.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentMessageProducer commentMessageProducer;

    @GetMapping("/comment_{commentId}")
    public String getComment(@PathVariable("commentId") int commentId) throws JMSException, JsonProcessingException {
        Comment comment = commentMessageProducer.sendGetCommentMessage(commentId);
        return "comment/comment_info";
    }

    @GetMapping("/list")
    public String getCommentList(@RequestParam Map<String, String> requestParams) throws JMSException, JsonProcessingException {
        List<Comment> commentList = commentMessageProducer.sendGetCommentListMessage(requestParams);
        return "comment/comment_list";
    }
}
