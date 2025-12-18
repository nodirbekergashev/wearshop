package com.powerofwear.dto.auth.token;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenCreateDto {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
