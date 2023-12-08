package com.example.salesrecord.controllers;

import com.example.salesrecord.DTO.ResponseDto;
import com.example.salesrecord.DTO.UserDto;
import com.example.salesrecord.exception.AlreadyExistsException;
import com.example.salesrecord.models.Product;
import com.example.salesrecord.models.User;
import com.example.salesrecord.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Админ консоль", description = "Операции администратора")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/user")
    @Operation(summary = "Создать пользователя")
    public ResponseEntity<ResponseDto> createNewUser(@RequestBody UserDto userDto) throws AlreadyExistsException {
        adminService.createUser(userDto);

        return ResponseEntity.ok(new ResponseDto("User successfully created"));
    }

    @PutMapping("/user/{id}")
    @Operation(summary = "Изменить пользователя")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        adminService.updateUser(id, userDto);

        return ResponseEntity.ok(new ResponseDto("User successfully updated"));
    }

    @GetMapping("/users")
    @Operation(summary = "Получить всех пользователей")
    public ResponseEntity<List<User>> users() {
        List<User> users = adminService.findUsers();

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "Удалить пользователя")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable String id) {
        adminService.deleteUser(id);

        return ResponseEntity.ok(new ResponseDto("User successfully deleted"));
    }
}
