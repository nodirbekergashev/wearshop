package com.powerofwear.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import com.powerofwear.common.enums.ProductStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProductResponseDto {

    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    private List<ProductImageResponseDto> images = new ArrayList<>();
    private ProductPriceResponseDto price = new ProductPriceResponseDto();
    private Integer stockQuantity;
    private ProductStatus status;
    private List<ProductAttributeResponseDto> attributes = new ArrayList<>();
    private String firstImageUrl;
    private Instant createdAt;
    private Instant updatedAt;
}
