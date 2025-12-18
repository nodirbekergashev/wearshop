package com.powerofwear.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.powerofwear.dto.wishlist.WishlistResponseDto;
import com.powerofwear.entity.Product;
import com.powerofwear.entity.Wishlist;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WishlistMapper {

    public WishlistResponseDto toDto(Wishlist wishlist) {
        Set<Long> productIds = wishlist.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        return WishlistResponseDto.builder()
                .id(wishlist.getId())
                .userId(wishlist.getUser().getId())
                .productIds(productIds)
                .build();
    }
}
