package com.powerofwear.validator;

import org.springframework.stereotype.Component;
import com.powerofwear.dto.user.UserCreateDto;
import com.powerofwear.dto.user.UserUpdateDto;
import com.powerofwear.exceptions.ValidationException;

@Component
public class UserValidator {

    public void validateOnCreate(UserCreateDto dto) {
        if (dto.getFirstName() == null || dto.getFirstName().isBlank())
            throw new ValidationException("Full name cannot be empty");

        if (dto.getPassword() == null || dto.getPassword().length() < 6)
            throw new ValidationException("Password must be at least 6 characters");
    }

    public void validateOnUpdate(UserUpdateDto dto) {
        if (dto.getPassword() != null && dto.getPassword().length() < 6)
            throw new ValidationException("Password must be at least 6 characters");
    }
}
