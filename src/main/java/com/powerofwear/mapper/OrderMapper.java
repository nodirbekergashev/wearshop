package com.powerofwear.mapper;

import com.powerofwear.entity.Cart;
import com.powerofwear.entity.Order;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import com.powerofwear.common.enums.OrderStatus;
import com.powerofwear.common.enums.PaymentStatus;
import com.powerofwear.dto.order.OrderResponseDto;
import com.powerofwear.dto.order.OrderSummaryDto;
import com.powerofwear.dto.order_item.OrderItemResponseDto;
import com.powerofwear.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderResponseDto toResponseDto(Order order) {
        if (order == null) return null;

        OrderResponseDto dto = new OrderResponseDto();

        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());

        // User
//        if (order.getUser() != null) {
//            dto.setUserId(order.getUser().getId());
//            dto.setUserFullName(order.getUser().getFullName());
//        }

        dto.setStatus(order.getStatus());
        dto.setPaymentStatus(order.getPaymentStatus());

        dto.setTotalPrice(order.getTotalPrice() != null ? order.getTotalPrice() : BigDecimal.ZERO);

        if (order.getDeliveryAddress() != null) {
            dto.setDeliveryAddressId(order.getDeliveryAddress().getId());
        }

        dto.setDeliveryType(order.getDeliveryType());
        dto.setTrackingNumber(order.getTrackingNumber());
        dto.setTransactionId(order.getTransactionId());
        dto.setPaymentDate(order.getPaymentDate());
        dto.setNotes(order.getNotes());
        dto.setCancellationReason(order.getCancellationReason());
        dto.setDeliveredAt(order.getDeliveredAt());
        dto.setCompletedAt(order.getCompletedAt());
        dto.setCancelledAt(order.getCancelledAt());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());

        // Payment Method
//        if (order.getPaymentMethod() != null) {
//            dto.setPaymentMethodId(order.getPaymentMethod().getId());
//            dto.setPaymentMethodName(order.getPaymentMethod().getName());
//        }

        // Order Items
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            List<OrderItemResponseDto> itemDtos = order.getItems().stream()
                    .map(orderItemMapper::toResponseDto)
                    .toList();
            dto.setItems(itemDtos);
        } else {
            throw new IllegalArgumentException("Order has no items");
        }


        return dto;
    }

    public OrderSummaryDto toSummaryDto(Order order) {
        if (order == null) return null;

        return OrderSummaryDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .itemCount(order.getItems() != null ? order.getItems().size() : 0)
                .build();
    }

    public Order fromCreateDto(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(getTotal(cart.getItems()));
        order.setItems(getItems(cart.getItems(), order));

        String transactionId = simulatePayment(order);

        order.setStatus(OrderStatus.PROCESSING);
        order.setPaymentStatus(PaymentStatus.SUCCESS);
        order.setTransactionId(transactionId);
        order.setPaymentDate(LocalDateTime.now());

        return order;

    }

    private BigDecimal getTotal(List<CartItem> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items) {
            total = total.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }


    private List<OrderItem> getItems(List<CartItem> items, Order order) {

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setAmount(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getProduct().getPrice());
            orderItem.setTotal(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private String simulatePayment(Order order) {
        System.out.printf("Simulating payment for Order #%d with total %.2f%n",
                order.getOrderNumber(), order.getTotalPrice());
        return "txn_" + UUID.randomUUID();
    }
}
