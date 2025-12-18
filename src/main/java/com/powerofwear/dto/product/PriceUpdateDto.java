package com.powerofwear.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PriceUpdateDto {
    private Long productId;
    private Long priceId;
    private BigDecimal comparePrice;
    private String currency;
}
