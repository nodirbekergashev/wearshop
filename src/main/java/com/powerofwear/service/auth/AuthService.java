package com.powerofwear.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.powerofwear.dto.auth.AuthResponse;
import com.powerofwear.dto.auth.UserLoginDto;
import com.powerofwear.dto.auth.token.TokenResponseDto;
import com.powerofwear.dto.user.UserCreateDto;
import com.powerofwear.entity.User;
import com.powerofwear.service.UserService;
import com.powerofwear.utils.JwtUtils;





@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticates a user by username and password, and generates access and refresh tokens.
     * The JwtUtils is updated to return TokenDto, which we handle here.
     * @param loginDto The user login credentials.
     * @return AuthResponse containing tokens and user details.
     */
    public AuthResponse login(UserLoginDto loginDto) {
        User user = userService.findByUsername(loginDto.getPhoneNumber());

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        TokenResponseDto accessTokenDto = jwtUtils.generateAccessToken(user);
        TokenResponseDto refreshTokenDto = jwtUtils.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessTokenDto.getToken())
                .refreshToken(refreshTokenDto.getToken())
                .username(user.getUsername())
                .roles(user.getRoles())
                .expiresAt(accessTokenDto.getExpiry())
                .build();
    }

    /**
     * Registers a new user, encodes the password, saves the user, and generates tokens.
     * @param userCreateDto The DTO containing new user details.
     */
    public void register(UserCreateDto userCreateDto) {
        String encodedPassword = passwordEncoder.encode(userCreateDto.getPassword());
        userCreateDto.setPassword(encodedPassword);
        userService.save(userCreateDto);
    }
}