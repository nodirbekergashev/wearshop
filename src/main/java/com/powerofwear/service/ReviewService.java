package com.powerofwear.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.powerofwear.dto.review.ReviewRequestDto;
import com.powerofwear.dto.review.ReviewResponseDto;

public interface ReviewService {
    ReviewResponseDto createReview(ReviewRequestDto requestDto);
    ReviewResponseDto getReviewById(Long id);
    Page<ReviewResponseDto> getReviewsByProductId(Long productId, Pageable pageable);
    Page<ReviewResponseDto> getReviewsByUserId(Long userId, Pageable pageable);
    ReviewResponseDto updateReview(Long id, ReviewRequestDto requestDto);
    void deleteReview(Long id);
}
