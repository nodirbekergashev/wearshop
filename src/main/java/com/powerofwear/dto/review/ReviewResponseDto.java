package com.powerofwear.dto.review;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDto {
    private Long productId;
    private Long userId;
    private Integer rating;
    private String comment;
}
