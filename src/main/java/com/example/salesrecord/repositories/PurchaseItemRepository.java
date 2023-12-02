package com.example.salesrecord.repositories;

import com.example.salesrecord.models.PurchaseItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper
public interface PurchaseItemRepository {
    List<PurchaseItem> findByPurchaseId(UUID purchaseId);
}
