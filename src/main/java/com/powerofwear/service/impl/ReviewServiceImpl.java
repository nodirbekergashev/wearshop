package com.powerofwear.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.powerofwear.dto.review.ReviewRequestDto;
import com.powerofwear.dto.review.ReviewResponseDto;
import com.powerofwear.entity.Product;
import com.powerofwear.entity.Review;
import com.powerofwear.entity.User;
import com.powerofwear.exceptions.ResourceNotFoundException;
import com.powerofwear.mapper.ReviewMapper;
import com.powerofwear.repository.ProductRepository;
import com.powerofwear.repository.ReviewRepository;
import com.powerofwear.repository.UserRepository;
import com.powerofwear.service.ReviewService;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ReviewResponseDto createReview(ReviewRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + requestDto.getProductId()));
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + requestDto.getUserId()));

        Review review = reviewMapper.toEntity(requestDto);
        review.setProduct(product);
        review.setUser(user);
        review = reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    @Override
    public ReviewResponseDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));
        return reviewMapper.toDto(review);
    }

    @Override
    public Page<ReviewResponseDto> getReviewsByProductId(Long productId, Pageable pageable) {
        return reviewRepository.findByProductId(productId, pageable)
                .map(reviewMapper::toDto);
    }

    @Override
    public Page<ReviewResponseDto> getReviewsByUserId(Long userId, Pageable pageable) {
        return reviewRepository.findByUserId(userId, pageable)
                .map(reviewMapper::toDto);
    }

    @Override
    public ReviewResponseDto updateReview(Long id, ReviewRequestDto requestDto) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + requestDto.getProductId()));
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + requestDto.getUserId()));

        existingReview.setRating(requestDto.getRating());
        existingReview.setComment(requestDto.getComment());
        existingReview.setProduct(product);
        existingReview.setUser(user);

        existingReview = reviewRepository.save(existingReview);
        return reviewMapper.toDto(existingReview);
    }

    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review not found with ID: " + id);
        }
        reviewRepository.deleteById(id);
    }
}
