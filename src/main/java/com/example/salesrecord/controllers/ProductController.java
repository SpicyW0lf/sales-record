package com.example.salesrecord.controllers;

import com.example.salesrecord.DTO.ProductDto;
import com.example.salesrecord.DTO.ResponseDto;
import com.example.salesrecord.models.Category;
import com.example.salesrecord.models.Product;
import com.example.salesrecord.models.User;
import com.example.salesrecord.repositories.ProductRepository;
import com.example.salesrecord.repositories.UserRepository;
import com.example.salesrecord.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserRepository userRepository;

    @GetMapping("/product/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @PostMapping("/product")
    public ResponseEntity<ResponseDto> createProduct(@RequestBody @Valid ProductDto productDto) {
       return ResponseEntity.ok(new ResponseDto(String.valueOf(productService.createProduct(productDto))));
    }

    @GetMapping("/user")
    public ResponseEntity<User> findUser() {
        return ResponseEntity.ok(userRepository.findByUsername("test"));
    }
}
