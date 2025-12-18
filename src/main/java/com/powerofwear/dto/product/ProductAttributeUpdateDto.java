package com.powerofwear.dto.product;


import lombok.Data;

@Data
public class ProductAttributeUpdateDto {
    private Long id;
    private String attributeKey;
    private String attributeValue;
}
