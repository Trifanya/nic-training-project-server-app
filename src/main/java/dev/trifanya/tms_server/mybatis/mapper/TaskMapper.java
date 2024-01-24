package dev.trifanya.tms_server.mybatis.mapper;

import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.model.task.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskMapper {
    String FIND_USER_BY_ID_METHOD_NAME = "dev.trifanya.tms_server.mybatis.mapper.UserMapper.findUserById";

    @Select("SELECT * FROM task")
    @Results(id="TaskResult", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "status", column = "status"),
            @Result(property = "priority", column = "priority"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "deadline", column = "deadline"),
            @Result(property = "author", javaType = User.class,
                    column = "author_id", one=@One(select = FIND_USER_BY_ID_METHOD_NAME)),
            @Result(property = "performer", javaType = User.class,
                    column = "performer_id", one=@One(select = FIND_USER_BY_ID_METHOD_NAME))
    })
    List<Task> findAllTasks();

    @Select("SELECT * FROM task WHERE author_id = #{authorId}")
    @ResultMap("TaskResult")
    /*@Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "status", column = "status"),
            @Result(property = "priority", column = "priority"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "deadline", column = "deadline"),
            @Result(property = "author", javaType = User.class, column = "author_id",
                    one=@One(select = FIND_USER_BY_ID_METHOD_NAME)),
            @Result(property = "performer", javaType = User.class, column = "performer_id",
                    one=@One(select = FIND_USER_BY_ID_METHOD_NAME))
    })*/
    List<Task> findAllTasksByAuthorId(long authorId);

    @Select("SELECT * FROM task WHERE performer_id = #{performerId}")
    @ResultMap("TaskResult")
    /*@Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "status", column = "status"),
            @Result(property = "priority", column = "priority"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "deadline", column = "deadline"),
            @Result(property = "author", javaType = User.class,
                    column = "author_id", one=@One(select = FIND_USER_BY_ID_METHOD_NAME)),
            @Result(property = "performer", javaType = User.class,
                    column = "performer_id", one=@One(select = FIND_USER_BY_ID_METHOD_NAME))
    })*/
    List<Task> findAllTasksByPerformerId(long performerId);

    @Select("SELECT * FROM task WHERE id = #{id}")
    /*@Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "status", column = "status"),
            @Result(property = "priority", column = "priority"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "deadline", column = "deadline"),
            @Result(property = "author", javaType = User.class, column = "author_id",
                    one=@One(select = FIND_USER_BY_ID_METHOD_NAME)),
            @Result(property = "performer", javaType = User.class, column = "performer_id",
                    one=@One(select = FIND_USER_BY_ID_METHOD_NAME))
    })*/
    @ResultMap("TaskResult")
    Optional<Task> findTaskById(long id);

    @Insert("INSERT INTO task (title, description, status, priority, created_at, deadline, author_id, performer_id)" +
            "VALUES (#{title}, #{description}, #{status}, #{priority}, #{createdAt}, #{deadline}, #{author.id}, #{performer.id})")
    void saveTask(Task taskToSave);

    @Update("UPDATE task SET title = #{title}, description = #{description}, status = #{status}, " +
            "priority = #{priority}, created_at = #{createdAt}, deadline = #{deadline}, " +
            "author_id = #{author.id}, performer_id = #{performer.id} WHERE id = #{id}")
    void updateTask(Task updatedTask);

    @Delete("DELETE FROM task WHERE id = #{id}")
    void deleteTaskById(long id);
}
