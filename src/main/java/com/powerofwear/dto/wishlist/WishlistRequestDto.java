package com.powerofwear.dto.wishlist;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishlistRequestDto {
    @NotNull(message = "User ID cannot be null")
    Long userId;

    @NotNull(message = "Product ID cannot be null")
    Long productId;
}
