package com.powerofwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.powerofwear.entity.Product;
import com.powerofwear.entity.ProductDetail;

import java.util.List;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetail, Long> {


    List<ProductDetail> findByProductId(Long productId);





    @Query("SELECT DISTINCT p FROM Product p JOIN p.productDetails pd WHERE pd.attributeKey = :key")
    List<Product> findProductsByAttributeKey(@Param("key") String key);


    @Modifying
    @Query("DELETE FROM ProductDetail pd WHERE pd.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}
