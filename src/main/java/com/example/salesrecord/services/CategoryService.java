package com.example.salesrecord.services;

import com.example.salesrecord.DTO.CategoryDto;
import com.example.salesrecord.exception.NotFoundException;
import com.example.salesrecord.models.Category;
import com.example.salesrecord.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public int createCategory(CategoryDto categoryDto) {
        return categoryRepository.save(Category.of(
                categoryDto.getName(),
                categoryDto.getType()
        ));
    }

    public Category findCategory(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(NotFoundException::new);
    }

    public void deleteCategory(String name) {
        categoryRepository.deleteByName(name);
    }

    public void updateCategory(CategoryDto categoryDto, String name) {
        categoryRepository.updateCategory(Category.of(
                        categoryDto.getName(),
                        categoryDto.getType()
                ),
                name);
    }
}
