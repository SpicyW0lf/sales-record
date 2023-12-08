package com.example.salesrecord.repositories;

import com.example.salesrecord.models.Product;
import com.example.salesrecord.models.Role;
import com.example.salesrecord.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepository {
    Optional<User> findByUsername(String username);

    @Insert("INSERT INTO users(id, username, password)" +
            " values (#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}, #{username}," +
            "#{password})")
    Integer save(User user);

    @Insert("INSERT INTO user_roles(user_id, role_id) values (#{user.id}, #{role.id})")
    Integer saveRoleForUser(User user, Role role);

    List<User> findAll();

    Optional<User> findById(String id);

    @Update("UPDATE users SET username=#{username}, password=#{password}")
    void update(User user);

    @Delete("DELETE FROM user_roles WHERE user_id=#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}")
    void deleteUserRoles(String id);

    @Delete("DELETE FROM users WHERE id=#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}")
    void deleteById(String id);
}
