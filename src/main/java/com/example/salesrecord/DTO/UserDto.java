package com.example.salesrecord.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Пользователь")
public class UserDto {
    @Schema(description = "Имя пользователя")
    private String username;
    @Schema(description = "Пароль")
    private String password;
    @Schema(description = "Роли")
    private List<String> roles;
}
