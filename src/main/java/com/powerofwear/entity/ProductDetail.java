package com.powerofwear.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.powerofwear.common.base.BaseEntity;

@Entity
@Table(name = "product_details", indexes = @Index(columnList = "product_id"))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "attribute_key", nullable = false, length = 100)
    private String attributeKey;

    @Column(name = "attribute_value", columnDefinition = "TEXT")
    private String attributeValue;
}