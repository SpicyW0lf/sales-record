package com.example.salesrecord.controllers;

import com.example.salesrecord.DTO.CategoryDto;
import com.example.salesrecord.DTO.ResponseDto;
import com.example.salesrecord.models.Category;
import com.example.salesrecord.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Управление категориями", description = "Операции над категориями")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/category/{name}")
    @Operation(summary = "Получить категорию")
    public ResponseEntity<Category> getCategory(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.findCategory(name));
    }

    @PostMapping("/category")
    @Operation(summary = "Создать категорию")
    public ResponseEntity<ResponseDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return ResponseEntity.ok(new ResponseDto(String.valueOf(categoryService.createCategory(categoryDto))));
    }

    @DeleteMapping("/category/{name}")
    @Operation(summary = "Удалить категорию")
    public ResponseEntity<ResponseDto> deleteCategory(@PathVariable String name) {
        categoryService.deleteCategory(name);

        return ResponseEntity.ok(new ResponseDto("Category successfully deleted"));
    }

    @PutMapping("/category/{name}")
    @Operation(summary = "Изменить категорию")
    public ResponseEntity<ResponseDto> updateCategory(@PathVariable String name, @RequestBody @Valid CategoryDto categoryDto) {
        categoryService.updateCategory(categoryDto, name);

        return ResponseEntity.ok(new ResponseDto("Category updated successfully"));
    }
}
