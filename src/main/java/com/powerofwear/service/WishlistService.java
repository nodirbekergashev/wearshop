package com.powerofwear.service;

import com.powerofwear.dto.wishlist.WishlistRequestDto;
import com.powerofwear.dto.wishlist.WishlistResponseDto;

public interface WishlistService {
    WishlistResponseDto addProductToWishlist(WishlistRequestDto requestDto);
    WishlistResponseDto removeProductFromWishlist(WishlistRequestDto requestDto);
    WishlistResponseDto getWishlistByUserId(Long userId);
    void clearWishlist(Long userId);

}
