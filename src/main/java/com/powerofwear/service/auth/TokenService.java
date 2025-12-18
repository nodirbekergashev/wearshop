package com.powerofwear.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.powerofwear.dto.auth.token.RefreshTokenDto;
import com.powerofwear.dto.auth.token.TokenResponseDto;
import com.powerofwear.entity.User;
import com.powerofwear.repository.UserRepository;
import com.powerofwear.utils.JwtUtils;


/**
 * Service class responsible for handling all business logic related to token management,
 * including refresh token validation, and access token generation.
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository; // To fetch the user object
    private final JwtUtils jwtUtils;           // Utility class for JWT operations

    /**
     * Validates the refresh token and issues a new access token and rotating refresh token.
     *
     * @param refreshToken The refresh token from the client request.
     * @return A DTO containing the new access token and the renewed refresh token.
     */
    public RefreshTokenDto refreshTokens(String refreshToken) {

        if (!jwtUtils.isTokenValid(refreshToken)) {
            throw new RuntimeException("Refresh token is invalid or has expired.");
        }

        String username = jwtUtils.extractUsername(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found for token subject: " + username));

        TokenResponseDto newAccessTokenResponseDto = jwtUtils.generateAccessToken(user);

        TokenResponseDto newRefreshTokenResponseDto = jwtUtils.generateRefreshToken(user);

        return RefreshTokenDto.builder()
                .accessToken(newAccessTokenResponseDto.getToken())
                .refreshToken(newRefreshTokenResponseDto.getToken())
                .tokenType("Bearer")
                .build();
    }

}
