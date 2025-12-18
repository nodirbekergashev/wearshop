package com.powerofwear.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.powerofwear.dto.category.CategoryCreateDto;
import com.powerofwear.dto.category.CategoryResponseDto;
import com.powerofwear.dto.category.CategoryUpdateDto;
import com.powerofwear.dto.product.ProductResponseDto;
import com.powerofwear.entity.Category;
import com.powerofwear.exceptions.ResourceNotFoundException;
import com.powerofwear.mapper.CategoryMapper;
import com.powerofwear.repository.CategoryRepository;
import com.powerofwear.service.CategoryService;
import com.powerofwear.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final ProductService productService;


    @Override
    public CategoryResponseDto save(CategoryCreateDto dto) {
        Category category = mapper.fromCreateDto(dto);

        validateUniqueSlug(null, category.getSlug());
        Category saved = repository.save(category);
        return mapper.toResponseDto(saved);
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryUpdateDto dto) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        String newSlug = category.getSlug();
        if (dto.getName() != null) {
            newSlug = mapper.generateSlug(dto.getName());
        }
        validateUniqueSlug(id, newSlug);

        mapper.fromUpdateDto(dto, category);


        Category saved = repository.save(category);
        return mapper.toResponseDto(saved);
    }

    @Override
    public void delete(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (productService.countByCategoryId(id) > 0) {
            throw new IllegalStateException("Cannot delete category with products");
        }
        repository.delete(category);
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }

    @Override
    public List<CategoryResponseDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @Override
    public Page<ProductResponseDto> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productService.getProductsByCategory(categoryId, pageable);
    }

    private void validateUniqueSlug(Long excludeId, String slug) {
        Long idToExclude = excludeId != null ? excludeId : -1L;
        if (repository.existsBySlugAndIdNot(slug, idToExclude)) {
            throw new IllegalArgumentException("Slug '" + slug + "' is already taken");
        }
    }
}