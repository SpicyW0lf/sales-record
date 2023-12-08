package com.example.salesrecord.services;

import com.example.salesrecord.DTO.ProductDto;
import com.example.salesrecord.exception.NotFoundException;
import com.example.salesrecord.models.Category;
import com.example.salesrecord.models.Product;
import com.example.salesrecord.repositories.CategoryRepository;
import com.example.salesrecord.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void createProductShouldThrowErrorIfCategoryNotExists() {
        doReturn(Optional.empty()).when(categoryRepository).findByName(Mockito.any());

        assertThrows(NotFoundException.class, () -> productService.createProduct(new ProductDto()));
    }

    @Test
    void createProductShouldSaveProduct() {
        doReturn(Optional.of(new Category())).when(categoryRepository).findByName(Mockito.any());

        productService.createProduct(new ProductDto());

        verify(productRepository).save(Mockito.any(), Mockito.any());
    }

    @Test
    void findAllProductsShouldReturnProducts() {
        doReturn(List.of(new Product(), new Product())).when(productRepository).findAllProducts();

        assertEquals(2, productService.findAllProducts().size());
    }

    @Test
    void deleteProductShouldCallDelete() {
        productService.deleteProduct("prod");

        verify(productRepository).delete(Mockito.any());
    }

    @Test
    void updateProductShouldThrowError() {
        ProductDto dto = new ProductDto();
        dto.setCategoryName("cat");
        doReturn(Optional.empty()).when(categoryRepository).findByName(Mockito.any());

        assertThrows(NotFoundException.class, () -> productService.updateProduct(dto, ""));
    }


}