package com.powerofwear.mapper;

import org.springframework.stereotype.Component;
import com.powerofwear.common.enums.ProductStatus;
import com.powerofwear.dto.product.*;
import com.powerofwear.entity.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    // =====================================================================
    // CREATE: ProductCreateDto → Product
    // =====================================================================
    public Product fromCreateDto(ProductCreateDto dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setName(dto.getName());
        product.setStatus(dto.getStatus() != null ? dto.getStatus() : ProductStatus.DRAFT);

        // Price
        if (dto.getPrice() != null) {
            ProductPrice price = toProductPriceEntity(dto.getPrice(), product);
            product.setPrices(new ArrayList<>(List.of(price)));
        } else {
            product.setPrices(new ArrayList<>());
        }

        return product;
    }

    // =====================================================================
    // UPDATE: ProductUpdateDto → Product (faqat to'ldirilgan maydonlarni yangilaydi)
    // =====================================================================
    public void fromUpdateDto(ProductUpdateDto dto, Product product) {
        if (dto == null || product == null) return;

        if (dto.getName() != null && !dto.getName().isBlank()) {
            product.setName(dto.getName());
        }
        if (dto.getStatus() != null) {
            product.setStatus(dto.getStatus());
        }
    }

    // =====================================================================
    // Entity → Response DTO
    // =====================================================================
    public ProductResponseDto toDto(Product product) {
        if (product == null) return null;

        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setStatus(product.getStatus());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }

        // Active price topish
        ProductPrice activePrice = findActivePrice(product);
        if (activePrice != null) {
            ProductPriceResponseDto price = dto.getPrice();
            price.setId(activePrice.getId());
            price.setPrice(activePrice.getPrice());
            price.setComparePrice(activePrice.getComparePrice());
            price.setCurrency(activePrice.getCurrency() != null ? activePrice.getCurrency() : "USD");
            price.setStartDate(activePrice.getStartDate());
            price.setEndDate(activePrice.getEndDate());
            price.setActive(activePrice.isActive());
        } else {
            throw new IllegalArgumentException("There is no active price belongs ro the product");
        }

        dto.setFirstImageUrl(findPrimaryImageUrl(product));

        dto.setImages(toImageResponseDtoList(product.getImages()));

        dto.setAttributes(toAttributeResponseDtoList(product.getProductDetails()));

        return dto;
    }

    public List<ProductResponseDto> toResponseDtoList(List<Product> products) {
        if (products == null) return List.of();
        return products.stream()
                .map(this::toDto)
                .toList();
    }

    // =====================================================================
    // Helper Methods
    // =====================================================================

    private ProductPrice findActivePrice(Product product) {
        if (product.getPrices() == null || product.getPrices().isEmpty()) return null;
        return product.getPrices().stream()
                .filter(ProductPrice::isActive)
                .findFirst()
                .orElse(null);
    }

    private String findPrimaryImageUrl(Product product) {
        if (product.getImages() == null || product.getImages().isEmpty()) return null;

        return product.getImages().stream()
                .filter(ProductImage::isPrimary)
                .map(ProductImage::getUrl)
                .findFirst()
                .orElse(product.getImages().getFirst().getUrl()); // birinchisi
    }


    private List<ProductImage> toProductImageEntitiesUpdate(List<ProductImageUpdateDto> dtos, Product product) {
        if (dtos == null) return new ArrayList<>();
        return dtos.stream()
                .map(dto -> {
                    ProductImage img = new ProductImage();
                    img.setId(dto.getId());
                    img.setUrl(dto.getUrl());
                    img.setAltText(dto.getAltText());
                    img.setPrimary(dto.isPrimary());
                    img.setPosition(dto.getPosition());
                    img.setProduct(product);
                    return img;
                })
                .toList();
    }

    private List<ProductImageResponseDto> toImageResponseDtoList(List<ProductImage> images) {
        if (images == null) return List.of();
        return images.stream()
                .filter(image -> !image.isDeleted())
                .map(img -> new ProductImageResponseDto(
                        img.getId(),
                        img.getUrl(),
                        img.getAltText(),
                        img.isPrimary(),
                        img.getPosition()
                ))
                .toList();
    }


    private List<ProductAttributeResponseDto> toAttributeResponseDtoList(List<ProductDetail> details) {
        if (details == null) return List.of();
        return details.stream()
                .filter(detail -> !detail.isDeleted())
                .map(d -> {
                    ProductAttributeResponseDto dto = new ProductAttributeResponseDto();
                    dto.setId(d.getId());
                    dto.setAttributeKey(d.getAttributeKey());
                    dto.setAttributeValue(d.getAttributeValue());
                    return dto;
                })
                .toList();
    }

    // Price
    private ProductPrice toProductPriceEntity(PriceCreateDto dto, Product product) {
        ProductPrice price = new ProductPrice();
        price.setPrice(dto.getPrice());
        price.setComparePrice(dto.getComparePrice());
        price.setCurrency(dto.getCurrency() != null ? dto.getCurrency() : "USD");
        price.setStartDate(dto.getStartDate() != null ? dto.getStartDate() : Instant.now());
        price.setActive(true);
        price.setProduct(product);
        return price;
    }

    private void updateOrCreatePrice(PriceUpdateDto dto, Product product) {
        if (dto == null) return;

        ProductPrice activePrice = findActivePrice(product);

        if (activePrice != null && dto.getPriceId() != null && dto.getPriceId().equals(activePrice.getId())) {
            // Update existing active price
            if (dto.getComparePrice() != null) {
                activePrice.setComparePrice(dto.getComparePrice());
            }
            if (dto.getCurrency() != null && !dto.getCurrency().isBlank()) {
                activePrice.setCurrency(dto.getCurrency());
            }
        } else if (dto.getPriceId() == null && activePrice != null) {
            // Deactivate old price and create new one if price needs to change
            activePrice.setActive(false);
            activePrice.setEndDate(Instant.now());

            // Create new price
            ProductPrice newPrice = new ProductPrice();
            newPrice.setPrice(activePrice.getPrice()); // Keep same price unless provided
            newPrice.setComparePrice(dto.getComparePrice() != null ? dto.getComparePrice() : activePrice.getComparePrice());
            newPrice.setCurrency(dto.getCurrency() != null ? dto.getCurrency() : activePrice.getCurrency());
            newPrice.setStartDate(Instant.now());
            newPrice.setActive(true);
            newPrice.setProduct(product);

            product.getPrices().add(newPrice);
        }
    }
}
