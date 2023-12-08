package com.example.salesrecord.repositories;

import com.example.salesrecord.models.PurchaseItem;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface PurchaseItemRepository {
    List<PurchaseItem> findByPurchaseId(UUID purchaseId);
    @Select("SELECT * FROM purchase_item WHERE purchase_id=#{purchaseId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler} " +
            "AND product_id=#{productId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}")
    @ResultMap("purchaseItemMap")
    Optional<PurchaseItem> findByPurchaseIdAndProductId(UUID purchaseId, UUID productId);

    @Update("UPDATE purchase_item SET qty=#{qty} WHERE " +
            "purchase_id=#{purchaseId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler} " +
            "AND product_id=#{productId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}")
    void updateItemQty(@Param("qty") int qty, @Param("purchaseId") UUID purchaseId, @Param("productId") UUID productId);

    @Insert("INSERT INTO purchase_item(purchase_id, product_id, refunded, qty) values (" +
            "#{purchaseId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}," +
            "#{productId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}," +
            "false," +
            "#{qty})")
    Integer save(UUID purchaseId, UUID productId, int qty);

    @Delete("DELETE FROM purchase_item WHERE purchase_id=#{purchaseId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}")
    void deleteByPurchaseId(UUID purchaseId);

    @Delete("DELETE FROM purchase_item" +
            " WHERE purchase_id=#{purchaseId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}," +
            "product_id=#{productId, javaType=java.util.UUID, jdbcType=OTHER, typeHandler=UuidTypeHandler}")
    void deleteByPurchaseIdAndProductId(UUID purchaseId, UUID productId);

    @Delete("DELETE FROM purchase_item")
    void clear();
}
