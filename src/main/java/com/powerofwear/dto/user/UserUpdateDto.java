package com.powerofwear.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {

    private String fullName;

    private String username;

    @Email(message = "Email format is invalid")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
