package com.example.salesrecord.services;

import com.example.salesrecord.DTO.ProductDto;
import com.example.salesrecord.exception.NotFoundException;
import com.example.salesrecord.models.Category;
import com.example.salesrecord.models.Product;
import com.example.salesrecord.repositories.CategoryRepository;
import com.example.salesrecord.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public int createProduct(ProductDto productDto) {
        Category category = categoryRepository.findByName(productDto.getCategoryName())
                .orElseThrow(NotFoundException::new);

        return productRepository.save(Product.of(productDto), category.getId());
    }

    public List<Product> findAllProducts() {
        return productRepository.findAllProducts();
    }

    public void updateProduct(ProductDto productDto, String id) {
        Category category = categoryRepository.findByName(productDto.getCategoryName())
                .orElseThrow(NotFoundException::new);

        productRepository.update(Product.of(productDto), id, category.getId());
    }

    public void deleteProduct(String id) {
        productRepository.delete(id);
    }
}
