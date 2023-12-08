package com.example.salesrecord.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Продукт")
public class ProductDto {
    @Schema(description = "Название")
    @NotEmpty(message = "name should not be empty")
    private String name;
    @Min(1)
    @Schema(description = "Количество")
    private int qty;
    @NotEmpty(message = "product code should not be empty")
    @Schema(description = "Код продукта")
    private String productCode;
    @NotNull
    @Schema(description = "Цена")
    private double price;
    @Schema(description = "Категория")
    @NotEmpty(message = "category name should not be empty")
    private String categoryName;
}
