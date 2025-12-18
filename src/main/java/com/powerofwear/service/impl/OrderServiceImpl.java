package com.powerofwear.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.powerofwear.common.enums.OrderStatus;
import com.powerofwear.dto.address.AddressCreateDto;
import com.powerofwear.dto.order.OrderResponseDto;
import com.powerofwear.entity.*;
import com.powerofwear.exceptions.ResourceNotFoundException;
import com.powerofwear.mapper.OrderMapper;
import com.powerofwear.repository.AddressRepository;
import com.powerofwear.repository.OrderItemRepository;
import com.powerofwear.repository.OrderRepository;
import com.powerofwear.service.CartService;
import com.powerofwear.service.OrderNumberGeneratorService;
import com.powerofwear.service.OrderService;
import com.powerofwear.service.ProductService;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final OrderNumberGeneratorService orderNumberGenerator;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;


    @Override
    @Transactional
    public OrderResponseDto createOrder(Long userId, AddressCreateDto dto) {
        Cart cart = cartService.getCart(userId);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create an order from an empty cart.");
        }

        Order order = orderMapper.fromCreateDto(cart);


        order.setOrderNumber(orderNumberGenerator.generate());
        Address address = toAddress(dto);
        address.setUser(cart.getUser());

        addressRepository.save(address);
        order.setDeliveryAddress(address);

//
//        order.getItems().forEach(item -> {
//            if (!stockReduced) {
//                throw new IllegalStateException("Failed to reduce stock for product: " + item.getProduct().getName());
//            }
//        });

        Order processedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(processedOrder.getItems());

        cartService.clearCart(userId);
        return orderMapper.toResponseDto(processedOrder);
    }


    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
        return orderMapper.toResponseDto(order);
    }

    @Override
    public Page<OrderResponseDto> getOrderHistoryForUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Order> orderPage = orderRepository.findByUserId(userId, pageable);
        return orderPage.map(orderMapper::toResponseDto);
    }

    @Override
    public OrderResponseDto cancelOrder(Long orderId, String cancellationReason) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        order.setStatus(OrderStatus.CANCELLED);
        order.setCancellationReason(cancellationReason);
        order.setCancelledAt(LocalDateTime.now());
        orderRepository.save(order);
        return orderMapper.toResponseDto(order);
    }

    @Override
    public Page<OrderResponseDto> getAllOrders(OrderStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Order> orderPage;

        if (status != null) {
            orderPage = orderRepository.findByStatus(status, pageable);
        } else {
            orderPage = orderRepository.findAll(pageable);
        }

        return orderPage.map(orderMapper::toResponseDto);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
        order.setStatus(newStatus);
        orderRepository.save(order);
        return orderMapper.toResponseDto(order);
    }

    private Address toAddress(AddressCreateDto dto) {
        Address address = new Address();
        address.setDistrict(dto.getDistrict());
        address.setStreet(dto.getStreet());
        address.setHouseNumber(dto.getHouseNumber());
        return address;
    }


}
