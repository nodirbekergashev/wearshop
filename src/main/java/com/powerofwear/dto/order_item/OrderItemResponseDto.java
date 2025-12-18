package com.powerofwear.dto.order_item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {

    private Long id;

    private Long productId;
    private String productName;
    private String imageUrl;

    private BigDecimal unitPrice;
    private Integer amount;
    private BigDecimal total;

    private Long orderId;
}
