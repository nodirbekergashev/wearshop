package com.powerofwear.mapper;

import org.springframework.stereotype.Component;
import com.powerofwear.dto.user.UserCreateDto;
import com.powerofwear.dto.user.UserResponseDto;
import com.powerofwear.dto.user.UserUpdateDto;
import com.powerofwear.entity.User;

@Component
public class UserMapper {

    public User fromCreateDto(UserCreateDto dto) {
        User user = new User();
        user.setUsername(dto.getPhoneNumber());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(dto.getPassword());
        return user;
    }

    public void fromUpdateDto(User user, UserUpdateDto dto) {
//        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
//        if (dto.getUsername() != null) user.setUsername(dto.getUsername());
//        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
//        if (dto.getPassword() != null) user.setPassword(dto.getPassword());
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
//        dto.setId(user.getId());
//        dto.setFullName(user.getFullName());
//        dto.setUsername(user.getUsername());
//        dto.setEmail(user.getEmail());
        return dto;
    }

    public User fromResponseDto(UserResponseDto dto) {
        User user = new User();
//        user.setId(dto.getId());
//        user.setFullName(dto.getFullName());
//        user.setUsername(dto.getUsername());
//        user.setEmail(dto.getEmail());
        return user;
    }
}
