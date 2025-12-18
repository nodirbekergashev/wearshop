package com.powerofwear.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductImageCreateDto {
    @NotBlank(message = "Image URL is required")
    private String url;

    private String altText;
    private Boolean primary = false;
    private Integer position;
}