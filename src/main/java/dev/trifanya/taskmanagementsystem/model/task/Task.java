package dev.trifanya.taskmanagementsystem.model.task;

import dev.trifanya.taskmanagementsystem.model.Comment;
import dev.trifanya.taskmanagementsystem.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "task")
@Accessors(chain = true)
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "performer_id", referencedColumnName = "id")
    private User performer;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;
}
