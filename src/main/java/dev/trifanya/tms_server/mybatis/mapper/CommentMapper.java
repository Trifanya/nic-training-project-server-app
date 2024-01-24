package dev.trifanya.tms_server.mybatis.mapper;

import dev.trifanya.tms_server.model.Comment;
import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.model.task.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    // public static final
    String FIND_TASK_BY_ID_METHOD_NAME = "dev.trifanya.tms_server.mybatis.mapper.TaskMapper.findTaskById";
    String FIND_USER_BY_ID_METHOD_NAME = "dev.trifanya.tms_server.mybatis.mapper.UserMapper.findUserById";

    @Select("SELECT * FROM comment")
    @Results(id = "commentResult", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "text", column = "text"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "task", javaType = Task.class, column = "task_id",
                    one=@One(select = FIND_TASK_BY_ID_METHOD_NAME)),
            @Result(property = "author", javaType = User.class, column = "author_id",
                    one=@One(select = FIND_USER_BY_ID_METHOD_NAME))
    })
    List<Comment> findAllComments();

    @Select("SELECT * FROM comment WHERE task_id = #{taskId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "text", column = "text"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "task", javaType = Task.class, column = "task_id",
                    one = @One(select = FIND_TASK_BY_ID_METHOD_NAME)),
            @Result(property = "author", javaType = User.class, column = "author_id",
                    one = @One(select = FIND_USER_BY_ID_METHOD_NAME))
    })
    List<Comment> findAllCommentsByTaskId(long taskId);

    @Select("SELECT * FROM comment WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "text", column = "text"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "task", javaType = Task.class, column = "task_id",
                    one=@One(select = FIND_TASK_BY_ID_METHOD_NAME)),
            @Result(property = "author", javaType = User.class, column = "author_id",
                    one=@One(select = FIND_USER_BY_ID_METHOD_NAME))
    })
    Optional<Comment> findCommentById(long id);

    @Insert("INSERT INTO comment (text, task_id, author_id, created_at)" +
            "VALUES (#{text}, #{task.id}, #{author.id}, #{createdAt})")
    void saveComment(Comment commentToSave);

    @Update("UPDATE comment SET text = #{text}, task_id = #{task.id}, author_id = #{author.id}, " +
            "created_at = #{createdAt} WHERE id = #{updatedComment.id}")
    void updateComment(Comment updatedComment);

    @Delete("DELETE FROM comment WHERE id = #{id}")
    void deleteCommentById(long id);
}
