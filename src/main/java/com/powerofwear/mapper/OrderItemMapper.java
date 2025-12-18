package com.powerofwear.mapper;

import org.springframework.stereotype.Component;
import com.powerofwear.dto.order_item.OrderItemResponseDto;
import com.powerofwear.entity.OrderItem;
import com.powerofwear.entity.Product;
import com.powerofwear.entity.ProductImage;

@Component
public class OrderItemMapper {


    public OrderItemResponseDto toResponseDto(OrderItem orderItem){
        OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
        orderItemResponseDto.setId(orderItem.getId());
        orderItemResponseDto.setProductId(orderItem.getProduct().getId());
        orderItemResponseDto.setProductName(orderItem.getProduct().getName());
        orderItemResponseDto.setImageUrl(productToImageUrl(orderItem.getProduct()));
        orderItemResponseDto.setUnitPrice(orderItem.getUnitPrice());
        orderItemResponseDto.setAmount(orderItem.getAmount());
        orderItemResponseDto.setTotal(orderItem.getTotal());
        orderItemResponseDto.setOrderId(orderItem.getOrder().getId());
        return orderItemResponseDto;
    }


    public String productToImageUrl(Product product) {
        if (product == null || product.getImages() == null || product.getImages().isEmpty()) {
            return null;
        }
        return product.getImages().stream()
                .filter(ProductImage::isPrimary)
                .findFirst()
                .map(ProductImage::getUrl)
                .orElse(product.getImages().getFirst().getUrl());
    }
}