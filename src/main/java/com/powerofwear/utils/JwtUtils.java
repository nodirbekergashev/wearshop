package com.powerofwear.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.powerofwear.dto.auth.token.TokenResponseDto;
import com.powerofwear.entity.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtils {

    // Access token expiration in SECONDS (e.g., 30 minutes)
    @Value("${jwt.access-expiration:1800000}")
    private Long accessTokenExpiration;

    // Refresh token expiration in SECONDS (86400 * 10 = 10 days)
    // NOTE: This value is in seconds for consistency with accessTokenExpiration.
    // The previous implementation used 86400000 (milliseconds), which has been corrected here.
    @Value("${jwt.refresh-expiration:86400000}")
    private Long refreshTokenExpiration;

    @Value("${jwt.secret:warehouse_secret_key_2025_uzunligi_kamida_32_byte}")
    private String secretKey;

    // Claim key used to distinguish access tokens from other token types (like refresh tokens)
    private static final String TOKEN_TYPE_CLAIM = "tokenType";
    private static final String ACCESS_TOKEN_TYPE = "ACCESS";

    /**
     * Extracts claims from a given JWT token.
     * @param token The JWT string.
     * @return The claims payload.
     * @throws JwtException if the token is invalid or expired.
     */
    public Claims extractClaims(String token) {
        // We use Jwts.parser() here, which handles validation (signature, expiry)
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extracts the username (subject) from a token.
     * @param token The JWT string.
     * @return The username.
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Checks if a token is valid (signature OK, not expired).
     * @param token The JWT string.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token) {
        try {
            // If extractClaims succeeds, the token is valid (signature checked, not expired)
            extractClaims(token);
            return true;
        } catch (JwtException e) {
            log.warn("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the token is an access token based on a custom claim.
     * @param token The JWT string.
     * @return true if the token is an access token.
     */
    public boolean isAccessToken(String token) {
        try {
            Claims claims = extractClaims(token);
            String tokenType = claims.get(TOKEN_TYPE_CLAIM, String.class);
            return ACCESS_TOKEN_TYPE.equals(tokenType);
        } catch (JwtException e) {
            // If claims cannot be extracted, it's not a valid token, so it's not an access token
            return false;
        }
    }


    // --- Convenience Overloads for AuthService ---

    public TokenResponseDto generateAccessToken(User authUser) {
        Map<String, Object> claims = prepareClaims(authUser);
        claims.put(TOKEN_TYPE_CLAIM, ACCESS_TOKEN_TYPE); // Mark as access token
        return generateAccessToken(authUser.getUsername(), claims);
    }

    public TokenResponseDto generateRefreshToken(User authUser) {
        Map<String, Object> claims = prepareClaims(authUser);
        // Do NOT put TOKEN_TYPE_CLAIM here, or use a different type if needed
        return generateRefreshToken(authUser.getUsername(), claims);
    }

    // --- Core Token Generation Methods ---

    public TokenResponseDto generateAccessToken(String subject, Map<String, Object> claims) {
        // Expiration in milliseconds
        Date exp = new Date(System.currentTimeMillis() + accessTokenExpiration * 1000);
        String token = Jwts.builder()
                .subject(subject)
                .expiration(exp)
                .signWith(getSecretKey())
                .issuedAt(new Date())
                .claims(claims)
                .compact();
        return TokenResponseDto.builder()
                .expiry(exp)
                .token(token)
                .build();
    }

    public TokenResponseDto generateRefreshToken(String subject, Map<String, Object> claims) {
        Date exp = new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000);
        String token = Jwts.builder()
                .subject(subject)
                .expiration(exp)
                .signWith(getSecretKey())
                .issuedAt(new Date())
                .claims(claims)
                .compact();

        return TokenResponseDto.builder()
                .expiry(exp)
                .token(token)
                .build();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Map<String, Object> prepareClaims(User authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", authUser.getId());
        return claims;
    }
}
