package com.example.salesrecord.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    @NotEmpty(message = "name should not be empty")
    private String name;
    @NotEmpty(message = "type should not be empty")
    private String type;
}
