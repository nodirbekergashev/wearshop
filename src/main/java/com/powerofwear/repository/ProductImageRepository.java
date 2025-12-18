package com.powerofwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.powerofwear.entity.ProductImage;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findByProductId(Long productId);


    List<ProductImage> findAllByProductId(Long productId);
}
