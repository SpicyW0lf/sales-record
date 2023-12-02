package com.example.salesrecord.repositories;

import com.example.salesrecord.models.Role;
import com.example.salesrecord.models.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    User findByUsername(String username);

    @Insert("INSERT INTO users(id, username, password)" +
            " values (#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}, #{username}," +
            "#{password})")
    Integer save(User user);
    @Insert("INSERT INTO user_roles(user_id, role_id) values (#{user.id}, #{role.id})")
    Integer saveRolesForUser(User user, Role role);
}
