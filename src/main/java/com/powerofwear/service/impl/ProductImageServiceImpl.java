package com.powerofwear.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.powerofwear.dto.product.ProductImageResponseDto;
import com.powerofwear.dto.product.ProductImageUpdateDto;
import com.powerofwear.entity.Product;
import com.powerofwear.entity.ProductImage;
import com.powerofwear.exceptions.ResourceNotFoundException;
import com.powerofwear.repository.ProductImageRepository;
import com.powerofwear.repository.ProductRepository;
import com.powerofwear.service.FileStorageService;
import com.powerofwear.service.ProductImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    @Override
    @Transactional
    public void addImages(Long productId, List<MultipartFile> files) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));


        int position = 1;

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String imageUrl;
            try {
                imageUrl = fileStorageService.store(file, productId.toString());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }

            ProductImage image = new ProductImage();
            image.setProduct(product);
            image.setUrl(imageUrl);
            image.setPosition(position + i);
            image.setPrimary(i == 0 && product.getImages().isEmpty());

            imageRepository.save(image);
        }
    }

    @Override
    @Transactional
    public void deleteImage(Long productId, Long imageId) {
        ProductImage img = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));

        if (!img.getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("Image does not belong to product");
        }

        imageRepository.delete(img);
    }

    @Override
    @Transactional
    public void setPrimaryImage(Long productId, Long imageId) {
        ProductImage image = imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Image", imageId));
        if (!image.getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("Image does not belong to product");
        }
        List<ProductImage> images = imageRepository.findAllByProductId(productId);
        for (ProductImage productImage : images) {
            if (productImage.equals(image)) {
                productImage.setPrimary(true);
            } else {
                productImage.setPrimary(false);
            }
        }
        imageRepository.saveAll(images);
    }

    @Override
    public void updateImages(Long productId, List<ProductImageUpdateDto> imageDtos) {
        if (imageDtos.isEmpty()) {
            return;
        }

        List<ProductImage> images = new ArrayList<>();
        for (ProductImageUpdateDto dto : imageDtos) {
            ProductImage img = imageRepository.findById(dto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
            if (!img.getProduct().getId().equals(productId)) {
                throw new IllegalArgumentException("Image does not belong to product");
            }
            img.setUrl(dto.getUrl());
            img.setPrimary(dto.isPrimary());
            img.setAltText(dto.getAltText());
            img.setPosition(dto.getPosition());
            images.add(img);
        }
        imageRepository.saveAll(images);
    }

    @Override
    public List<ProductImageResponseDto> getImages(Long productId) {
        return imageRepository.findByProductId(productId)
                .stream()
                .filter(img -> !img.isDeleted())
                .map(img -> new ProductImageResponseDto(img.getId(), img.getUrl(), img.getAltText(), img.isPrimary(), img.getPosition()))
                .toList();
    }
}
