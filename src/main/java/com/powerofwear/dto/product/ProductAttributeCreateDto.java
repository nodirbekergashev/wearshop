package com.powerofwear.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductAttributeCreateDto {
    @NotBlank
    private String attributeKey;

    @NotBlank
    private String attributeValue;
}