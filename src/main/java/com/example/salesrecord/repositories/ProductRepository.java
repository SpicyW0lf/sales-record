package com.example.salesrecord.repositories;

import com.example.salesrecord.models.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Mapper
public interface ProductRepository {
    @Select("SELECT * FROM product")
    @ResultMap("productMap")
    List<Product> findAllProducts();

    @Insert("INSERT INTO product(id, name, qty, productCode, price, categoryId)" +
            " values (#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}, #{name}," +
            " #{qty}, #{productCode}," +
            " #{price}, #{categoryId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler})")
    Integer save(Product product);

    Product findById(UUID id);

    @Update("UPDATE product SET qty=#{qty} WHERE id=#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}")
    void updateQty(@Param("qty") int qty, @Param("id") UUID id);
}
