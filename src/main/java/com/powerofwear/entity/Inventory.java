package com.powerofwear.entity;

import com.powerofwear.common.base.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "inventories")
public class Inventory extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(name = "current_stock", nullable = false)
    private Integer currentStock = 0;

    @Column(name = "reserved_stock", nullable = false)
    private Integer reservedStock = 0;

    @Column(name = "available_stock", nullable = false)
    private Integer availableStock = 0;

    @Column(name = "minimum_stock_level")
    private Integer minimumStockLevel = 5;

    @PreUpdate
    @PrePersist
    private void calculateAvailableStock() {
        this.availableStock = Math.max(0, this.currentStock - this.reservedStock);
    }
}