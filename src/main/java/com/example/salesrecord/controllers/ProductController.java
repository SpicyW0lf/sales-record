package com.example.salesrecord.controllers;

import com.example.salesrecord.DTO.ProductDto;
import com.example.salesrecord.DTO.ResponseDto;
import com.example.salesrecord.models.Category;
import com.example.salesrecord.models.Product;
import com.example.salesrecord.models.User;
import com.example.salesrecord.repositories.ProductRepository;
import com.example.salesrecord.repositories.UserRepository;
import com.example.salesrecord.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Управление продуктами", description = "Операции над продуктами")
public class ProductController {
    private final ProductService productService;
    private final UserRepository userRepository;

    @GetMapping("/product/all")
    @Operation(summary = "Все продукты", description = "Получить все продукты")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @PostMapping("/product")
    @Operation(summary = "Создать продукт")
    public ResponseEntity<ResponseDto> createProduct(@RequestBody @Valid ProductDto productDto) {
       return ResponseEntity.ok(new ResponseDto(String.valueOf(productService.createProduct(productDto))));
    }

    @PutMapping("/product/{id}")
    @Operation(summary = "Изменить продукт")
    public ResponseEntity<ResponseDto> updateProduct(@PathVariable String id, @RequestBody @Valid ProductDto productDto) {
        productService.updateProduct(productDto, id);

        return ResponseEntity.ok(new ResponseDto("Product updated successfully"));
    }

    @DeleteMapping("/product/{id}")
    @Operation(summary = "Удалить продукт")
    public ResponseEntity<ResponseDto> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok(new ResponseDto("Product deleted successfully"));
    }
}
