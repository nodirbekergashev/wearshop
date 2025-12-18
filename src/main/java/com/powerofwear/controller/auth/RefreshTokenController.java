package com.powerofwear.controller.auth;


import com.powerofwear.dto.auth.token.RefreshTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.powerofwear.service.auth.TokenService;


@RestController
@RequestMapping("/v1/auth/refresh-token")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final TokenService tokenService;

    public void name (String refreshToken){
        RefreshTokenDto refreshTokenDto = tokenService.refreshTokens(refreshToken);
    }

}
