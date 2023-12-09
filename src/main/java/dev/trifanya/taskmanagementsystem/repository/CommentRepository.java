package dev.trifanya.taskmanagementsystem.repository;

import dev.trifanya.taskmanagementsystem.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByTaskId(int taskId);
}
