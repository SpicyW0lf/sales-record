package com.example.salesrecord.repositories;

import com.example.salesrecord.models.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Mapper
public interface CategoryRepository {

    @Select("SELECT * FROM category WHERE name = #{name}")
    Optional<Category> findByName(@Param("name") String name);

    @Insert("INSERT INTO category(id, name, type) values (" +
            "#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}," +
            "#{name}, #{type})")
    Integer save(Category category);

    @Delete("DELETE FROM category WHERE category=#{name}")
    void deleteByName(String name);

    @Update("UPDATE category set name=#{name}, type=#{type} WHERE name=#{oldName}")
    void updateCategory(Category category, String oldName);

    @Select("SELECT * FROM category WHERE id=#{id}")
    Category findById(@Param("id") UUID id);
}
