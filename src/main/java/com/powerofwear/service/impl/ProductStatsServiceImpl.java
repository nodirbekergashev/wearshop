package com.powerofwear.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.powerofwear.dto.product.ProductStatsDto;
import com.powerofwear.repository.ProductRepository;
import com.powerofwear.service.ProductStatsService;

@Service
@RequiredArgsConstructor
public class ProductStatsServiceImpl implements ProductStatsService {

    private final ProductRepository productRepository;

    @Override
    public ProductStatsDto getStats() {
        return productRepository.getProductStatistics().orElseThrow(()->new RuntimeException("cannot get stats"));
    }
}
