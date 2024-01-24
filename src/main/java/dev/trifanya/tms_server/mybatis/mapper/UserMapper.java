package dev.trifanya.tms_server.mybatis.mapper;

import dev.trifanya.tms_server.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM tms_user")
    List<User> findAllUsers();

    @Select("SELECT * FROM tms_user WHERE id = #{id}")
    Optional<User> findUserById(long id);

    @Select("SELECT * FROM tms_user WHERE email = #{email}")
    Optional<User> findUserByEmail(String email);

    @Insert("INSERT INTO tms_user (name, surname, position, email, password)" +
            "VALUES (#{name}, #{surname}, #{position}, #{email}, #{password})")
    void saveUser(User userToSave);

    @Update("UPDATE tms_user SET name = #{name}, surname = #{surname}, " +
            "position = #{position}, email = #{email}, password = #{password}")
    void updateUser(User updatedUser);

    @Delete("DELETE FROM tms_user WHERE id = #{id}")
    void deleteUserById(long id);
}
