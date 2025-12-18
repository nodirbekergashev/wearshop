package com.powerofwear.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAttributeResponseDto {
    private Long id;
    private String attributeKey;
    private String attributeValue;
}