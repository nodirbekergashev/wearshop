package com.powerofwear.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.powerofwear.dto.product.PriceCreateDto;
import com.powerofwear.dto.product.PriceUpdateDto;
import com.powerofwear.entity.Product;
import com.powerofwear.entity.ProductPrice;
import com.powerofwear.exceptions.ResourceNotFoundException;
import com.powerofwear.mapper.PriceMapper;
import com.powerofwear.repository.ProductPriceRepository;
import com.powerofwear.repository.ProductRepository;
import com.powerofwear.service.ProductPriceService;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    private final ProductPriceRepository priceRepository;
    private final ProductRepository productRepository;
    private final PriceMapper priceMapper;


    @Override
    @Transactional // Ensure this method is transactional
    public Product addPrice(PriceCreateDto price) {
        Product product = productRepository.findById(price.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // 1. GUARANTEED DEACTIVATION: Use the UPDATE query to set old price active=false.
        // This is executed as a single DML statement immediately.
        priceRepository.deactivateActivePricesByProductId(price.getProductId());

        // 2. Create and insert the NEW price (which defaults to active=true)
        ProductPrice newPrice = priceMapper.fromCreateDto(price);
        newPrice.setProduct(product);
        // newPrice.active is true by default

        priceRepository.save(newPrice);

        return product;
    }
// You no longer need the 'deactivateOldPrices' helper method or 'priceRepository.saveAll(prices)'.


    // In ProductPriceRepository

    @Override
    @Transactional
    public void updateCurrentPrice(PriceUpdateDto dto) {
        ProductPrice current = priceRepository.findById(dto.getPriceId())
                .orElseThrow(() -> new ResourceNotFoundException("Active price not found"));
        if (current.isActive()) {
            current.setComparePrice(dto.getComparePrice());
            current.setCurrency(current.getCurrency());
            priceRepository.save(current);
        } else {
            throw new IllegalCallerException("Deactivated price cannot be changed");
        }
    }
}
