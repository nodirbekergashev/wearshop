package com.powerofwear.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreateDto {

    @NotBlank(message = "Region is required")
    private String region;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "Street is required")
    private String street;

    private String houseNumber;

    private String postalCode;

    @NotBlank(message = "User ID is required")
    private Long userId;
}