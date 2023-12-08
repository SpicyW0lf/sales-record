package com.example.salesrecord.repositories;

import com.example.salesrecord.models.Purchase;
import org.apache.ibatis.annotations.*;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface PurchaseRepository {
    List<Purchase> findAll();

    Optional<Purchase> findById(UUID id);

    @Select("SELECT * FROM purchase WHERE user_id=#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler} " +
            "AND total=0")
    Optional<Purchase> findCurrentPurchase(UUID id);

    @Insert("INSERT INTO purchase(id, date, total, user_id) VALUES (" +
            "#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}," +
            "#{date}," +
            "#{total}," +
            "#{user.id})")
    Integer save(Purchase purchase);

    @Delete("DELETE FROM purchase WHERE id=#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}")
    void delete(Purchase purchase);

    @Update("UPDATE purchase SET total=#{total} WHERE id=#{id, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}")
    void updateTotal(Purchase purchase);

    @Delete("DELETE FROM purchase")
    void clear();

    @Select("SELECT sum(total) FROM purchase WHERE date=#{date}")
    Integer countTodayTotal(LocalDate now);
}