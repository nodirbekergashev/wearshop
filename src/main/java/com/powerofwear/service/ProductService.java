package com.powerofwear.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.powerofwear.common.enums.ProductStatus;
import com.powerofwear.dto.product.*;
import com.powerofwear.entity.Product;

import java.util.List;

public interface ProductService {

    ProductResponseDto create(ProductCreateDto dto/*, List<MultipartFile> images*/);

    ProductResponseDto getProductById(Long id);

    Product findById(Long id);

    Page<ProductResponseDto> getAllProducts(String name, Long categoryId,
                                            ProductStatus status, Pageable pageable);

    ProductResponseDto updateProduct(Long id, ProductUpdateDto dto);

    void deleteProduct(Long id);

    ProductResponseDto updatePrice(PriceUpdateDto dto);

    ProductResponseDto addNewPrice(PriceCreateDto dto);

    ProductResponseDto addImages(Long productId, List<MultipartFile> images);

    Page<ProductResponseDto> getProductsBySeller(Long sellerId, Pageable pageable);

    ProductResponseDto updateImages(Long productId, List<ProductImageUpdateDto> images);

    ProductResponseDto deleteImage(Long productId, Long imageId);

    ProductResponseDto setPrimaryImage(Long productId, Long imageId);


    List<ProductResponseDto> getLowStockProducts(Integer threshold);

    Page<ProductResponseDto> searchProducts(String query, Pageable pageable);

    Page<ProductResponseDto> getProductsByCategory(Long categoryId, Pageable pageable);

    List<ProductResponseDto> getActiveProducts(Integer limit);

    Page<ProductResponseDto> getProductsByStatus(ProductStatus status, Pageable pageable);

    void bulkUpdateStatus(List<Long> productIds, ProductStatus status);

    void bulkDelete(List<Long> productIds);

    ProductStatsDto getProductStatistics();

    Page<ProductResponseDto> getProductsForReview(Pageable pageable);

    Page<ProductResponseDto> findProductsInCategoryTree(Long categoryId, Pageable pageable);

    Long countByCategoryId(Long categoryId);

//    Page<ProductResponseDto> getAll(Pageable pageable);
}
