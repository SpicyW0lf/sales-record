package com.example.salesrecord.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Ответ")
public class ResponseDto {
    @Schema(description = "Сообщение")
    private String message;
}
