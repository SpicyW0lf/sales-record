package com.example.salesrecord.controllers;

import com.example.salesrecord.DTO.CategoryDto;
import com.example.salesrecord.DTO.ResponseDto;
import com.example.salesrecord.models.Category;
import com.example.salesrecord.repositories.CategoryRepository;
import com.example.salesrecord.services.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/category/{name}")
    public ResponseEntity<Category> getCategory(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.findCategory(name));
    }

    @PostMapping("/category")
    public ResponseEntity<ResponseDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return ResponseEntity.ok(new ResponseDto(String.valueOf(categoryService.createCategory(categoryDto))));
    }
}
