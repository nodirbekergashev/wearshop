package com.powerofwear.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.powerofwear.common.enums.ProductStatus;

import java.util.List;


@Data
public class ProductCreateDto {

    private Long userId;

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 255)
    private String name;

    @NotNull @Min(1)
    private Long categoryId;

    @NotNull @Min(0)
    private Integer stockQuantity;

    @NotNull
    private ProductStatus status;

    private PriceCreateDto price;

    private List<ProductAttributeCreateDto> attributes;
}
