package com.powerofwear.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.powerofwear.dto.wishlist.WishlistRequestDto;
import com.powerofwear.dto.wishlist.WishlistResponseDto;
import com.powerofwear.entity.Product;
import com.powerofwear.entity.User;
import com.powerofwear.entity.Wishlist;
import com.powerofwear.exceptions.ResourceNotFoundException;
import com.powerofwear.mapper.WishlistMapper;
import com.powerofwear.repository.ProductRepository;
import com.powerofwear.repository.UserRepository;
import com.powerofwear.repository.WishlistRepository;
import com.powerofwear.service.WishlistService;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistMapper mapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishlistRepository repository;


    @Override
    public WishlistResponseDto addProductToWishlist(WishlistRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getUserId()));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + dto.getProductId()));

        Wishlist wishlist = repository.findByUserId(dto.getUserId())
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setUser(user);
                    return newWishlist;
                });

        wishlist.getProducts().add(product);
        repository.save(wishlist);
        return mapper.toDto(wishlist);
    }

    @Override
    public WishlistResponseDto removeProductFromWishlist(WishlistRequestDto dto) {
        Wishlist wishlist = repository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist cannot found for userId: " + dto.getUserId()));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product cannot found for productId: " + dto.getProductId()));
        
        wishlist.getProducts().remove(product);
        repository.save(wishlist);
        return mapper.toDto(wishlist);
    }

    @Override
    public WishlistResponseDto getWishlistByUserId(Long userId) {
        Wishlist wishlist = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist cannot found for userId: " + userId));
        return mapper.toDto(wishlist);
    }

    @Override
    public void clearWishlist(Long userId) {
        Wishlist wishlist = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist cannot found for userId: " + userId));
        wishlist.setProducts(new HashSet<>());
        repository.save(wishlist);
    }
}
