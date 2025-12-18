package com.powerofwear.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceResponseDto {
    private Long id;
    private BigDecimal price;
    private BigDecimal comparePrice;
    private String currency;
    private Instant startDate;
    private Instant endDate;
    private boolean isActive;
}
