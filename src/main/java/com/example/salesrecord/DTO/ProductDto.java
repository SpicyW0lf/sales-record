package com.example.salesrecord.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    @NotEmpty(message = "name should not be empty")
    private String name;
    @Min(1)
    private int qty;
    @NotEmpty(message = "product code should not be empty")
    private String productCode;
    @NotNull
    private double price;
    @NotEmpty(message = "category name should not be empty")
    private String categoryName;
}
