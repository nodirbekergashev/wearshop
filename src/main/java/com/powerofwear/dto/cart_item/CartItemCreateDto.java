package com.powerofwear.dto.cart_item;

import lombok.Data;

@Data
public class CartItemCreateDto {
    private Long productId;
    private int amount;
}
