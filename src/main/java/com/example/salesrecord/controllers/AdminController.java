package com.example.salesrecord.controllers;

import com.example.salesrecord.DTO.ResponseDto;
import com.example.salesrecord.DTO.UserDto;
import com.example.salesrecord.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/user")
    public ResponseEntity<ResponseDto> createNewUser(@RequestBody UserDto userDto) {
        adminService.createUser(userDto);

        return ResponseEntity.ok(new ResponseDto("User successfully created"));
    }
}
