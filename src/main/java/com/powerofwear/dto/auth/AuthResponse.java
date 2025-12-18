package com.powerofwear.dto.auth;

import lombok.Builder;
import lombok.Data;
import com.powerofwear.entity.Role;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String username;
    private Set<Role> roles;
    private Date expiresAt;
}