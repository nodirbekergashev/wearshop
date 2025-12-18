package com.powerofwear.dto.wishlist;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishlistResponseDto {
    Long id;
    Long userId;
    Set<Long> productIds;

}
