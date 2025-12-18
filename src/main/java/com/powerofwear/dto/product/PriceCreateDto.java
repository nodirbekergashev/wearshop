package com.powerofwear.dto.product;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
public class PriceCreateDto {
    private Long productId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;

    private BigDecimal comparePrice;

    @Builder.Default
    private String currency = "USD";

    @Builder.Default
    private Instant startDate = Instant.now();

}
