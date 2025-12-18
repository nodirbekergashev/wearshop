package com.powerofwear.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.powerofwear.common.base.BaseEntity;

@Entity
@Table(name = "product_images", indexes = @Index(columnList = "product_id"))
@Getter
@Setter
public class ProductImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String url;

    private String altText;

    @Column(name = "is_primary", nullable = false)
    private boolean primary = false;  // ← primitive!

    @Column(nullable = false)
    private int position = 0;

    // Optional: ensure only one primary per product
    @PreUpdate @PrePersist
    private void validatePrimary() {
        if (primary && position != 0) {
            position = 0; // force first position
        }
    }
}
