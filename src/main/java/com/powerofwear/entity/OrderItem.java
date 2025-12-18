package com.powerofwear.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import com.powerofwear.common.base.BaseEntity;

import java.math.BigDecimal;


@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem extends BaseEntity {


    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal unitPrice;

    @NotNull
    @Min(1)
    @Max(950)
    @Column(nullable = false)
    private Integer amount;

    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal total;

    @Size(max = 500)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Override
    public void softDelete() {
        throw new UnsupportedOperationException("Order items cannot be deleted");
    }
}
