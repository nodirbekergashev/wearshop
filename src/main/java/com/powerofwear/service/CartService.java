package com.powerofwear.service;

import org.springframework.stereotype.Service;
import com.powerofwear.dto.cart.CartResponseDto;
import com.powerofwear.dto.cart_item.CartItemCreateDto;
import com.powerofwear.dto.cart_item.CartItemUpdateDto;
import com.powerofwear.entity.Cart;

@Service
public interface CartService {

    CartResponseDto createCart(Long userId);

    Cart getCart(Long userId);

    CartResponseDto getCartByUserId(Long userId);

    CartResponseDto addToCart(Long userId, CartItemCreateDto dto);

    CartResponseDto updateCartItem(Long userId, Long productId, CartItemUpdateDto dto);

    CartResponseDto removeFromCart(Long userId, Long productId);


    void clearCart(Long userId);
}
