package com.example.salesrecord.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private UUID id;
    private String name;
    private int qty;
    private String productCode;
    private double price;
    private Category category;

    public static Product of(String name, int qty, String productCode, double price, UUID categoryId) {
        return new Product(
                UUID.randomUUID(),
                name,
                qty,
                productCode,
                price,
                null
        );
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }
}
