package com.powerofwear.dto.product;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductStatsDto {
    private long totalProducts;
    private long activeProducts;
    private BigDecimal averagePrice;
    private Long productsInDraft;

    public ProductStatsDto(long totalProducts, long activeProducts, long outOfStockProducts, BigDecimal averagePrice, Long productsInDraft){
        this.totalProducts = totalProducts;
        this.activeProducts = activeProducts;
        this.averagePrice = averagePrice;
        this.productsInDraft = productsInDraft;
    }
}
