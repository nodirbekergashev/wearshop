package com.powerofwear.validator;

import org.springframework.stereotype.Component;
import com.powerofwear.common.enums.ProductStatus;
import com.powerofwear.dto.product.ProductCreateDto;
import com.powerofwear.dto.product.ProductUpdateDto;
import com.powerofwear.entity.Product;

@Component
public class ProductValidator {

    public void validateOnCreate(ProductCreateDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        if (dto.getPrice() == null) {
            throw new IllegalArgumentException("Product price must be positive.");
        }
        if (dto.getCategoryId() == null) {
            throw new IllegalArgumentException("Product must belong to a category.");
        }
        if (dto.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("Product must have a seller.");
        }
    }

    public void validateOnUpdate(ProductUpdateDto dto) {
        if (dto.getName() != null && dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        if (dto.getStockQuantity() != null && dto.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
    }

    public void validateBeforeSave(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be positive.");
        }
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new IllegalArgumentException("Product must belong to a category.");
        }

        product.setStatus(ProductStatus.DRAFT);
    }
}
