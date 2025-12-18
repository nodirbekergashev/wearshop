package com.powerofwear.dto.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import com.powerofwear.dto.cart_item.CartItemResponseDto;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponseDto {
    private Long id;
    private Long userId;
    private Integer totalItems;
    private BigDecimal totalAmount;
    private List<CartItemResponseDto> items;
}
