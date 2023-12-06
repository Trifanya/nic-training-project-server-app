package dev.trifanya.taskmanagementsystem.model.task;

import dev.trifanya.taskmanagementsystem.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "task")
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

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User author;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User performer;
}
