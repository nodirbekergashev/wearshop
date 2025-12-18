package com.powerofwear.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.powerofwear.dto.cart.CartResponseDto;
import com.powerofwear.dto.cart_item.CartItemResponseDto;
import com.powerofwear.entity.Cart;
import com.powerofwear.entity.CartItem;
import com.powerofwear.entity.ProductImage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper {

    public CartResponseDto toDto(Cart cart) {
        if (cart == null) {
            return null;
        }

        CartResponseDto dto = new CartResponseDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser() != null ? cart.getUser().getId() : null);

        List<CartItemResponseDto> itemDtos = new ArrayList<>();
        if (cart.getItems() != null) {
            for (CartItem item : cart.getItems()) {
                itemDtos.add(toItemDto(item));
            }
        }
        dto.setItems(itemDtos);

        // Calculate totals
        dto.setTotalItems(calculateTotalItems(cart));
        dto.setTotalAmount(calculateTotalAmount(cart));

        return dto;
    }

    private CartItemResponseDto toItemDto(CartItem item) {
        if (item == null) {
            return null;
        }

        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setTotalPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

        if (item.getProduct() != null) {
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setImageUrl(findPrimaryImageUrl(item.getProduct().getImages()));
        }

        return dto;
    }

    private int calculateTotalItems(Cart cart) {
        if (cart.getItems() == null) {
            return 0;
        }
        return cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
    }

    private BigDecimal calculateTotalAmount(Cart cart) {
        if (cart.getItems() == null) {
            return BigDecimal.ZERO;
        }
        return cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String findPrimaryImageUrl(List<ProductImage> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.stream()
                .filter(ProductImage::isPrimary)
                .findFirst()
                .map(ProductImage::getUrl)
                .orElse(images.get(0).getUrl());
    }
}
