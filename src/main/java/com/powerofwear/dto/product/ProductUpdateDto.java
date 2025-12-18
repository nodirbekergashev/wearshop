package com.powerofwear.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.powerofwear.common.enums.ProductStatus;

@Data
public class ProductUpdateDto {

    @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters")
    private String name;

    @Min(value = 1, message = "Category ID must be greater than 0")
    private Long categoryId;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    private ProductStatus status;
}
