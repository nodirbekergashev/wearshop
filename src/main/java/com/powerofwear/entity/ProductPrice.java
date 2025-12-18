package com.powerofwear.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.powerofwear.common.base.BaseEntity;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "product_prices",
        indexes = @Index(columnList = "product_id"),
        uniqueConstraints = @UniqueConstraint(
                name = "uk_active_price",
                columnNames = {"product_id", "is_active"}
        ))
@Getter
@Setter
public class ProductPrice extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(precision = 10, scale = 2)
    private BigDecimal comparePrice;

    @Column(length = 5, nullable = false)
    private String currency = "USD";

    @Column(nullable = false)
    private Instant startDate = Instant.now();

    private Instant endDate;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;  // ← rename field to active
}
