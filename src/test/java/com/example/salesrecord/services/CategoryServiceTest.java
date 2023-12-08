package com.example.salesrecord.services;

import com.example.salesrecord.DTO.CategoryDto;
import com.example.salesrecord.exception.NotFoundException;
import com.example.salesrecord.models.Category;
import com.example.salesrecord.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @Test
    void createCategoryShouldCallSave() {
        categoryService.createCategory(new CategoryDto());

        verify(categoryRepository).save(Mockito.any());
    }

    @Test
    void findCategoryShouldThrowErrorIfNotExists() {
        doReturn(Optional.empty()).when(categoryRepository).findByName(Mockito.any());

        assertThrows(NotFoundException.class, () -> categoryService.findCategory("cat"));
    }

    @Test
    void findCategoryShouldReturnCategory() {
        Category category = new Category();

        doReturn(Optional.of(category)).when(categoryRepository).findByName(Mockito.any());

        assertEquals(category, categoryService.findCategory("cat"));
    }

    @Test
    void deleteCategoryShouldCallMethod() {
        categoryService.deleteCategory("cat");

        verify(categoryRepository).deleteByName(Mockito.any());
    }
}