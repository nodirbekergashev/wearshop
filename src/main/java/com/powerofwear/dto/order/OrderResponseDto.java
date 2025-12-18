package com.powerofwear.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.powerofwear.common.enums.DeliveryType;
import com.powerofwear.common.enums.OrderStatus;
import com.powerofwear.common.enums.PaymentStatus;
import com.powerofwear.dto.order_item.OrderItemResponseDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long id;
    private Long orderNumber;

    private Long userId;
    private String userFullName;           // optional - can be populated in service

    private OrderStatus status;
    private PaymentStatus paymentStatus;

    private BigDecimal totalPrice;


    private Long deliveryAddressId;
    private String deliveryAddressDetails;  // optional summary

    private DeliveryType deliveryType;
    private String trackingNumber;

    private LocalDateTime deliveredAt;
    private LocalDateTime completedAt;
    private LocalDateTime cancelledAt;

    private Long paymentMethodId;
    private String paymentMethodName;       // optional
    private String transactionId;
    private LocalDateTime paymentDate;

    private String notes;
    private String cancellationReason;

    private Instant createdAt;
    private Instant updatedAt;

    private List<OrderItemResponseDto> items;
}
