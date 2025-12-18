package com.powerofwear.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.powerofwear.dto.category.CategoryCreateDto;
import com.powerofwear.dto.category.CategoryResponseDto;
import com.powerofwear.dto.category.CategoryUpdateDto;
import com.powerofwear.dto.product.ProductResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto save(CategoryCreateDto dto);
    CategoryResponseDto update(Long id, CategoryUpdateDto dto);
    void delete(Long id);
    CategoryResponseDto getById(Long id);
    List<CategoryResponseDto> getAll();

    Page<ProductResponseDto> getProductsByCategory(Long categoryId, Pageable pageable);
}
