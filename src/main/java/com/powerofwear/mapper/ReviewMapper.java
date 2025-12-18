package com.powerofwear.mapper;

import org.springframework.stereotype.Component;
import com.powerofwear.dto.review.ReviewRequestDto;
import com.powerofwear.dto.review.ReviewResponseDto;
import com.powerofwear.entity.Review;

@Component
public class ReviewMapper {

    public Review toEntity(ReviewRequestDto dto){
        return Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();
    }
    public ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .productId(review.getProduct().getId())
                .userId(review.getUser().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
    }

}
