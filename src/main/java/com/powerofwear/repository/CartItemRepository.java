package com.powerofwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.powerofwear.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
//    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
//    List<CartItem> findByCartId(Long cartId);
//    void deleteByCartIdAndProductId(Long cartId, Long productId);
}
