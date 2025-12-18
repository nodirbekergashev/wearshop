package com.powerofwear.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.powerofwear.dto.cart.CartResponseDto;
import com.powerofwear.dto.cart_item.CartItemCreateDto;
import com.powerofwear.dto.cart_item.CartItemUpdateDto;
import com.powerofwear.entity.Cart;
import com.powerofwear.entity.CartItem;
import com.powerofwear.entity.Product;
import com.powerofwear.exceptions.ResourceNotFoundException;
import com.powerofwear.mapper.CartMapper;
import com.powerofwear.repository.CartRepository;
import com.powerofwear.repository.UserRepository;
import com.powerofwear.service.CartService;
import com.powerofwear.service.ProductService;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final CartMapper cartMapper;

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userRepository.getReferenceById(userId));
                    return cartRepository.save(newCart);
                });
    }

    @Override
    @Transactional
    public CartResponseDto createCart(Long userId) {
        Cart newCart = new Cart();
        newCart.setUser(userRepository.getReferenceById(userId));
        cartRepository.save(newCart);
        return cartMapper.toDto(newCart);
    }

    @Override
    public Cart getCart(Long userId) {
        return cartRepository.findCartByUserId(userId).orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public CartResponseDto getCartByUserId(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return cartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public CartResponseDto addToCart(Long userId, CartItemCreateDto dto) {
        Cart cart = getOrCreateCart(userId);
        Product product = productService.findById(dto.getProductId());


        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(dto.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            int newQty = item.getQuantity() + dto.getAmount();

                            item.setQuantity(newQty);
                        },
                        () -> {
                            CartItem newItem = new CartItem();
                            newItem.setCart(cart);
                            newItem.setProduct(product);
                            newItem.setQuantity(dto.getAmount());
                            cart.getItems().add(newItem);
                        }
                );

        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    public CartResponseDto updateCartItem(Long userId, Long productId, CartItemUpdateDto dto) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not in cart"));

        if (dto.getAmount() <= 0) {
            cart.getItems().remove(item);
        } else {

            item.setQuantity(dto.getAmount());
        }

        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    public CartResponseDto removeFromCart(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
