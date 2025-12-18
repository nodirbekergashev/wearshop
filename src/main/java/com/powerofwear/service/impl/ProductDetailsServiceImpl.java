package com.powerofwear.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.powerofwear.dto.product.ProductAttributeCreateDto;
import com.powerofwear.entity.Product;
import com.powerofwear.entity.ProductDetail;
import com.powerofwear.exceptions.ResourceNotFoundException;
import com.powerofwear.repository.ProductDetailsRepository;
import com.powerofwear.repository.ProductRepository;
import com.powerofwear.service.ProductDetailsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductRepository productRepository;
    private final ProductDetailsRepository detailsRepository;


    @Override
    public List<ProductDetail> findByProductId(Long productId) {
        return detailsRepository.findByProductId(productId);
    }

    @Override
    @Transactional
    public List<ProductDetail> createAttributes(Long productId, List<ProductAttributeCreateDto> attributes) {

        // 1. Fetch the managed Product entity reference efficiently
        // FindById is safer here if we need to guarantee existence.
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        // 2. Map DTOs to transient entities and collect them into a mutable list
        List<ProductDetail> newDetails = attributes.stream()
                .map(attribute -> {
                    ProductDetail detail = new ProductDetail();
                    detail.setProduct(product);
                    detail.setAttributeKey(attribute.getAttributeKey());
                    detail.setAttributeValue(attribute.getAttributeValue());
                    return detail;
                })
                .collect(Collectors.toList()); // ✅ Returns a MUTABLE list (or list-like structure)

        // 3. Explicitly persist the new entities efficiently
        // This inserts the details into the database.
        detailsRepository.saveAll(newDetails);
        return newDetails;
    }


}
