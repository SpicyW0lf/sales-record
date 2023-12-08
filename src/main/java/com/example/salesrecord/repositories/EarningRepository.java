package com.example.salesrecord.repositories;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

@Mapper
public interface EarningRepository {
    @Insert("INSERT INTO earnings(date, value) values (#{date}, #{value})")
    void insertTotal(LocalDate now, Integer value);
}
