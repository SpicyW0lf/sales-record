package com.example.salesrecord.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Покупка")
public class PurchaseItemDto {
    @Schema(description = "Код продукта")
    private String productCode;
    @Schema(description = "Количество")
    private int qty;
}
