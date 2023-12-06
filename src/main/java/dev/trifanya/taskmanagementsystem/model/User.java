package dev.trifanya.taskmanagementsystem.model;

import dev.trifanya.taskmanagementsystem.model.task.Task;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "tms_user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "performer")
    private List<Task> tasksToDo;
}
