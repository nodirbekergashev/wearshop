package com.powerofwear.service;


import org.springframework.web.multipart.MultipartFile;
import com.powerofwear.dto.product.ProductImageResponseDto;
import com.powerofwear.dto.product.ProductImageUpdateDto;

import java.util.List;
public interface ProductImageService {

    void addImages(Long productId, List<MultipartFile> files);

    void updateImages(Long productId, List<ProductImageUpdateDto> imageDtos);

    void deleteImage(Long productId, Long imageId);

    void setPrimaryImage(Long productId, Long imageId);

    List<ProductImageResponseDto> getImages(Long productId);
}
