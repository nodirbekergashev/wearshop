package com.powerofwear.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Username cannot be empty")
    private String phoneNumber;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
