package com.powerofwear.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.powerofwear.common.enums.ProductStatus;
import com.powerofwear.dto.product.*;
import com.powerofwear.entity.Category;
import com.powerofwear.entity.Product;
import com.powerofwear.entity.ProductDetail;
import com.powerofwear.exceptions.ResourceNotFoundException;
import com.powerofwear.mapper.ProductMapper;
import com.powerofwear.repository.CategoryRepository;
import com.powerofwear.repository.ProductRepository;
import com.powerofwear.repository.UserRepository;
import com.powerofwear.service.*;
import com.powerofwear.validator.ProductValidator;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;
    private final ProductValidator validator;

    private final ProductImageService productImageService;
    private final ProductPriceService productPriceService;
    private final ProductDetailsService productDetailsService;

    @Override
    @Transactional
    public ProductResponseDto create(ProductCreateDto dto) {
        validator.validateOnCreate(dto);
        Product product = mapper.fromCreateDto(dto);

        product.setCategory(categoryRepository.getReferenceById(dto.getCategoryId()));
        product.setSeller(userRepository.getReferenceById(dto.getUserId()));

        product = productRepository.save(product);
        Long productId = product.getId();

        if (dto.getAttributes() != null && !dto.getAttributes().isEmpty()) {
            List<ProductDetail> persistedAttributes = productDetailsService.createAttributes(productId, dto.getAttributes());
            product.setProductDetails(new ArrayList<>(persistedAttributes));
        }
        validator.validateBeforeSave(product);
        productRepository.save(product);

        return mapper.toDto(product);
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public Page<ProductResponseDto> getAllProducts(String name, Long categoryId, ProductStatus status, Pageable pageable) {
        return productRepository.findProductsWithFilters(name, categoryId, status, pageable)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductUpdateDto dto) {
        Product product = findById(id);
        mapper.fromUpdateDto(dto, product);

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + dto.getCategoryId()));
            product.setCategory(category);
        }
        productRepository.save(product);
        return getProductById(id);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = findById(id);
        product.softDelete();
        productRepository.save(product);
    }


    @Override
    @Transactional
    public ProductResponseDto updatePrice(PriceUpdateDto dto) {
        productPriceService.updateCurrentPrice(dto);
        return getProductById(dto.getProductId());
    }

    @Override
    public ProductResponseDto addNewPrice(PriceCreateDto dto) {
        return mapper.toDto(productPriceService.addPrice(dto));
    }


    @Override
    @Transactional
    public ProductResponseDto addImages(Long productId, List<MultipartFile> imageDtos) {
        productImageService.addImages(productId, imageDtos);
        return getProductById(productId);
    }

    @Override
    public Page<ProductResponseDto> getProductsBySeller(Long sellerId, Pageable pageable) {
        return productRepository.findAllBySellerId(sellerId, pageable).map(mapper::toDto);
    }

    @Override
    public ProductResponseDto updateImages(Long productId, List<ProductImageUpdateDto> imageDtos) {
        productImageService.updateImages(productId, imageDtos);
        return getProductById(productId);
    }

    @Override
    @Transactional
    public ProductResponseDto deleteImage(Long productId, Long imageId) {
        productImageService.deleteImage(productId, imageId);
        return getProductById(productId);
    }

    @Override
    @Transactional
    public ProductResponseDto setPrimaryImage(Long productId, Long imageId) {
        productImageService.setPrimaryImage(productId, imageId);
        return getProductById(productId);
    }


    @Override
    public List<ProductResponseDto> getLowStockProducts(Integer threshold) {
//        return productRepository.findByStockQuantityLessThan(threshold).stream()
//                .map(mapper::toDto)
//                .toList();
        return null;
    }


    @Override
    public Page<ProductResponseDto> searchProducts(String query, Pageable pageable) {
        return productRepository.searchProducts(query, pageable).map(mapper::toDto);
    }

    @Override
    public Page<ProductResponseDto> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable).map(mapper::toDto);
    }

    @Override
    public List<ProductResponseDto> getActiveProducts(Integer limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("createdAt").descending());
        return productRepository.findByStatus(ProductStatus.PUBLISHED, pageable).getContent().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Page<ProductResponseDto> getProductsByStatus(ProductStatus status, Pageable pageable) {
        return productRepository.findByStatus(status, pageable).map(mapper::toDto);
    }

    @Override
    @Transactional
    public void bulkUpdateStatus(List<Long> productIds, ProductStatus status) {
        productRepository.updateStatusForIds(productIds, status);
    }

    @Override
    @Transactional
    public void bulkDelete(List<Long> productIds) {
        productRepository.softDeleteByIds(productIds);
    }

    @Override
    public ProductStatsDto getProductStatistics() {
        return productRepository.getProductStatistics().orElse(null);
    }

    @Override
    public Page<ProductResponseDto> getProductsForReview(Pageable pageable) {
        return getProductsByStatus(ProductStatus.DRAFT, pageable);
    }

    @Override
    public Page<ProductResponseDto> findProductsInCategoryTree(Long categoryId, Pageable pageable) {
        return productRepository.findProductsInCategoryTree(categoryId, pageable).map(mapper::toDto);
    }

    @Override
    public Long countByCategoryId(Long categoryId) {
        return (long) productRepository.findAllByCategoryId(categoryId).size();
    }

//    @Override
//    public Page<ProductResponseDto> getAll(Pageable pageable) {
//        return productRepository.getAll(pageable).map(mapper::toDto);
//    }
}
