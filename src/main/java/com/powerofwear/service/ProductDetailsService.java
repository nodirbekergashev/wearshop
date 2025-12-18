package com.powerofwear.service;

import com.powerofwear.dto.product.ProductAttributeCreateDto;
import com.powerofwear.entity.ProductDetail;

import java.util.List;

public interface ProductDetailsService {
    List<ProductDetail> findByProductId(Long productId);
    List<ProductDetail> createAttributes(Long productId, List<ProductAttributeCreateDto> attributes);
}
