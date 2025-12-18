package com.powerofwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.powerofwear.entity.ProductPrice;

import java.util.List;

public interface ProductPriceRepository extends JpaRepository<ProductPrice,Long> {

    @Modifying
    @Query("UPDATE ProductPrice p SET p.active = false, p.endDate = CURRENT_TIMESTAMP WHERE p.product.id = :productId AND p.active = true")
    void deactivateActivePricesByProductId(@Param("productId") Long productId);
}
