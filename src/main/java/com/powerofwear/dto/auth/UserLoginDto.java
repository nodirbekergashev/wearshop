package com.powerofwear.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    private String phoneNumber;
    private String password;
}
