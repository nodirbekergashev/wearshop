package com.powerofwear.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.powerofwear.dto.category.CategoryCreateDto;
import com.powerofwear.dto.category.CategoryUpdateDto;
import com.powerofwear.exceptions.ValidationException;
import com.powerofwear.repository.CategoryRepository;


@Component
@RequiredArgsConstructor
public class CategoryValidator {

    private final CategoryRepository repository;

    public void validateCreate(CategoryCreateDto dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new ValidationException("Category name is required");
        }

        // check if category with same name already exists
        if (repository.findAll().stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(dto.getName()))) {
            throw new ValidationException("Category name already exists");
        }
    }

    public void validateUpdate(Long id, CategoryUpdateDto dto) {
        if (dto.getName() != null && dto.getName().isBlank()) {
            throw new ValidationException("Category name cannot be empty");
        }

        // check for duplicate name if name is being updated
        if (dto.getName() != null) {
            repository.findAll().stream()
                    .filter(c -> !c.getId().equals(id))
                    .filter(c -> c.getName().equalsIgnoreCase(dto.getName()))
                    .findAny()
                    .ifPresent(c -> {
                        throw new ValidationException("Category name already exists");
                    });
        }
    }
}
