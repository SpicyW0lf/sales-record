package com.example.salesrecord.repositories;

import com.example.salesrecord.models.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Mapper
public interface RoleRepository {
    @Select("SELECT * FROM role WHERE id=#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}")
    Role findById(UUID id);

    @Select("SELECT * FROM role WHERE name=#{name}")
    Role findByName(String name);
}
