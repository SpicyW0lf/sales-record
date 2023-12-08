package com.example.salesrecord.models;


import com.example.salesrecord.DTO.ProductDto;
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

    public static Product of(ProductDto productDto) {
        return new Product(
                UUID.randomUUID(),
                productDto.getName(),
                productDto.getQty(),
                productDto.getProductCode(),
                productDto.getPrice(),
                null
        );
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }
}
