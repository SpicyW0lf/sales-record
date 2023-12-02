package com.example.salesrecord.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseItem {
    private UUID id;
    private Purchase purchase;
    private Product product;
    private boolean refunded;
    private int qty;
}
