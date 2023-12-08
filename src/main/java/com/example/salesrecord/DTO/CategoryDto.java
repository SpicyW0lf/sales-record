package com.example.salesrecord.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Категория")
public class CategoryDto {
    @Schema(description = "Название категории")
    @NotEmpty(message = "name should not be empty")
    private String name;
    @Schema(description = "Тип категории")
    @NotEmpty(message = "type should not be empty")
    private String type;
}
