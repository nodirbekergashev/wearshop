// src/main/java/com.powerofwear.dto/order/OrderUpdateDto.java
package com.powerofwear.dto.order;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.powerofwear.common.enums.DeliveryType;
import com.powerofwear.common.enums.OrderStatus;
import com.powerofwear.common.enums.PaymentStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDto {

    private DeliveryType deliveryType;

    private OrderStatus status;

    private PaymentStatus paymentStatus;

    private String trackingNumber;

    private String transactionId;

    @Size(max = 500)
    private String notes;

    @Size(max = 500)
    private String cancellationReason;
}