package dev.trifanya.taskmanagementsystem.repository;

import dev.trifanya.taskmanagementsystem.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> getAllByAuthorId(int authorId);
    List<Task> getAllByPerformerId(int performerId);
}
