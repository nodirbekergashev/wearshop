package com.powerofwear.mapper;

import org.springframework.stereotype.Component;
import com.powerofwear.dto.product.PriceCreateDto;
import com.powerofwear.entity.ProductPrice;

import java.time.Instant;

@Component
public class PriceMapper {
    public ProductPrice fromCreateDto(PriceCreateDto price) {
        ProductPrice newPrice = new ProductPrice();
        newPrice.setPrice(price.getPrice());
        newPrice.setComparePrice(price.getComparePrice());
        newPrice.setStartDate(Instant.now());
        newPrice.setActive(true);
        newPrice.setCurrency(price.getCurrency());
        return newPrice;
    }
}
