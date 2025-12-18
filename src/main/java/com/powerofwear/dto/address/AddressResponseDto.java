package com.powerofwear.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {

    private Long id;

    private String region;
    private String district;
    private String street;
    private String houseNumber;
    private String postalCode;

    private Long userId;
    private String userFullName; // optional

    private String fullAddress; // e.g. "Tashkent, Chilanzar, Chapanata 45"

    private Instant createdAt;
    private Instant updatedAt;
}