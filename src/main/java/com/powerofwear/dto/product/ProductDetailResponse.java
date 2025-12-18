package com.powerofwear.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDetailResponse {
    private Long id;
    private String attributeKey;
    private String attributeValue;
}
