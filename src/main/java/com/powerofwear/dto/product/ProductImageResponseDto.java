package com.powerofwear.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageResponseDto {
    private Long id;
    private String imageUrl;
    private String altText;
    private boolean isPrimary;
    private Integer position;
}
