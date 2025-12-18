package com.powerofwear.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.powerofwear.common.enums.ProductStatus;
import com.powerofwear.dto.product.ProductStatsDto;
import com.powerofwear.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR lower(p.name) LIKE lower(concat('%', :name, '%'))) AND " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:status IS NULL OR p.status = :status)")
    Page<Product> findProductsWithFilters(
            @Param("name") String name,
            @Param("categoryId") Long categoryId,
            @Param("status") ProductStatus status,
            Pageable pageable
    );

    @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE lower(concat('%', :query, '%'))")
    Page<Product> searchProducts(@Param("query") String query, Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByStatus(ProductStatus productStatus, Pageable pageable);

    @Modifying
    @Query("UPDATE Product p SET p.status = :status WHERE p.id IN :productIds")
    void updateStatusForIds(@Param("productIds") List<Long> productIds, @Param("status") ProductStatus status);

    @Modifying
    @Query("UPDATE Product p SET p.deleted = true, p.deletedAt = CURRENT_TIMESTAMP WHERE p.id IN :productIds")
    void softDeleteByIds(@Param("productIds") List<Long> productIds);

    @Query("""
        SELECT NEW com.powerofwear.dto.product.ProductStatsDto(
            COUNT(p.id),
            SUM(CASE WHEN p.status = com.powerofwear.common.enums.ProductStatus.PUBLISHED THEN 1L ELSE 0L END),
            SUM(CASE WHEN p.inventory.availableStock = 0 THEN 1L ELSE 0L END),
            CAST((SELECT AVG(pp.price) FROM ProductPrice pp WHERE pp.active = TRUE) AS bigdecimal),
            SUM(CASE WHEN p.status = com.powerofwear.common.enums.ProductStatus.DRAFT THEN 1L ELSE 0L END)
        )
        FROM Product p
    """)
    Optional<ProductStatsDto> getProductStatistics();


    @Query(value = """
    WITH RECURSIVE category_tree AS (
        SELECT id FROM categories WHERE id = :categoryId
        UNION ALL
        SELECT c.id FROM categories c
        JOIN category_tree ct ON c.parent_id = ct.id
    )
    SELECT p.* FROM products p
    JOIN inventory i ON p.id = i.product_id
    WHERE p.category_id IN (SELECT id FROM category_tree)
      AND p.status = 'PUBLISHED'
      AND i.available_stock > 0
    """,
            countQuery = """
    WITH RECURSIVE category_tree AS (
        SELECT id FROM categories WHERE id = :categoryId
        UNION ALL
        SELECT c.id FROM categories c
        JOIN category_tree ct ON c.parent_id = ct.id
    )
    SELECT COUNT(*) FROM products p
    JOIN inventory i ON p.id = i.product_id
    WHERE p.category_id IN (SELECT id FROM category_tree)
      AND p.status = 'PUBLISHED'
      AND i.available_stock > 0
    """,
            nativeQuery = true)
    Page<Product> findProductsInCategoryTree(
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    List<Product> findAllByCategoryId(Long categoryId);

    Page<Product> findAllBySellerId(Long sellerId, Pageable pageable);
//
//    @Query("SELECT p FROM Product p WHERE p.deleted = false")
//    Page<Product> getAll(Pageable pageable);
}
