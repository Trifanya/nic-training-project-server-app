package dev.trifanya.taskmanagementsystem.repository;

import dev.trifanya.taskmanagementsystem.model.task.Task;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
    List<Task> getAllByAuthorId(Specification<Task> specification, PageRequest pageRequest);
    List<Task> getAllByPerformerId(Specification<Task> specification, PageRequest pageRequest);
}
