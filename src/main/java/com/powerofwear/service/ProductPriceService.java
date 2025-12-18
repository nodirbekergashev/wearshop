package com.powerofwear.service;

import com.powerofwear.dto.product.PriceCreateDto;
import com.powerofwear.dto.product.PriceUpdateDto;
import com.powerofwear.entity.Product;

public interface ProductPriceService {

    Product addPrice(PriceCreateDto priceCreateDto);

    void updateCurrentPrice(PriceUpdateDto priceUpdateDto);
}
