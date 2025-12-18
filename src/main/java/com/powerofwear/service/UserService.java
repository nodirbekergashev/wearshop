package com.powerofwear.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.powerofwear.dto.user.UserCreateDto;
import com.powerofwear.dto.user.UserResponseDto;
import com.powerofwear.dto.user.UserUpdateDto;
import com.powerofwear.entity.User;

public interface UserService {

    UserResponseDto save(UserCreateDto dto);

    UserResponseDto update(Long id, UserUpdateDto dto);

    void delete(Long id);

    UserResponseDto getById(Long id);

    Page<UserResponseDto> getAll(Pageable pageable);
    User findByUsername(String username);

    User findById(Long id);

    UserResponseDto getCurrentUser();
}
