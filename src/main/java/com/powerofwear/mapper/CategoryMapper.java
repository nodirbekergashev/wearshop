package com.powerofwear.mapper;

import com.powerofwear.dto.category.*;
import com.powerofwear.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public Category fromCreateDto(CategoryCreateDto dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setName(dto.getName());
        category.setSlug(generateSlug(dto.getName()));
        return category;
    }

    public void fromUpdateDto(CategoryUpdateDto dto, Category category) {
        if (dto == null || category == null) {
            return;
        }
        if (dto.getName() != null) {
            category.setName(dto.getName());
            category.setSlug(generateSlug(dto.getName()));
        }
    }

    public CategoryResponseDto toResponseDto(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .build();
    }

    public List<CategoryResponseDto> toDtoList(List<Category> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public String generateSlug(String name) {
        if (name == null) return null;
        return name.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}
