package com.powerofwear.dto.auth.token;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class TokenResponseDto {
    private String token;
    private Date expiry;
}
