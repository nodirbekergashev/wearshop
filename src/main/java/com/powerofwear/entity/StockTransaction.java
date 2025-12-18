package com.powerofwear.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.powerofwear.common.base.BaseEntity;
import com.powerofwear.common.enums.TransactionType;
import com.powerofwear.common.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "stock_transactions")
@Getter
@Setter
public class StockTransaction extends BaseEntity {

    // Qaysi inventory (mahsulot) uchun
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    // Harakat turi
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    // Miqdor (musbat = kirim, manfiy = chiqim)
    @Column(nullable = false)
    private Integer quantity;

    // Harakatdan oldingi qoldiq
    @Column(name = "stock_before", nullable = false)
    private Integer stockBefore;

    // Harakatdan keyingi qoldiq
    @Column(name = "stock_after", nullable = false)
    private Integer stockAfter;

    // Ajratilgan stock (agar bor bo'lsa)
    @Column(name = "reserved_before")
    private Integer reservedBefore = 0;

    @Column(name = "reserved_after")
    private Integer reservedAfter = 0;

    // Reference (qaysi buyurtma/xarid bilan bog'liq)
    @Column(name = "reference_id")
    private String referenceId; // ORDER_123, PURCHASE_456

    @Column(name = "reference_type")
    private String referenceType; // ORDER, PURCHASE, RETURN, ADJUSTMENT

    // Narx (agar xarid/kirim bo'lsa)
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    // Izoh
    @Column(columnDefinition = "TEXT")
    private String notes;

    // Kim qilgan
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by")
    private User performedBy;

    // Vaqt
    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate = Instant.now();

    // Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status = TransactionStatus.COMPLETED;

    // Manba (qayerdan)
    private String source; // SUPPLIER, WAREHOUSE, CUSTOMER_RETURN, DAMAGED_STOCK

    // Maqsad (qayerga)
    private String destination; // CUSTOMER, WAREHOUSE, DAMAGED, LOST

    // Transaction qiymatini hisoblash
    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        if (unitPrice != null && quantity != null) {
            this.totalPrice = unitPrice.multiply(
                    BigDecimal.valueOf(Math.abs(quantity))
            );
        }
    }

    // Transaction tavsifini olish
    @Transient
    public String getDescription() {
//        String productName = inventory.getProduct() != null
//                ? inventory.getProduct().getName()
//                : "Product";

        return String.format("%s: %s x%d",
                type.getDisplayName(),
//                productName,
                "pr",
                Math.abs(quantity)

        );
    }

    // Kirim/Chiqim aniqlash
    @Transient
    public boolean isIncoming() {
        return type.isIncoming();
    }

    @Transient
    public boolean isOutgoing() {
        return type.isOutgoing();
    }
}