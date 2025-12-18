package com.powerofwear.mapper;


import com.powerofwear.dto.role.RoleResponseDto;
import com.powerofwear.entity.Role;
import org.springframework.stereotype.Component;


@Component
public class RoleMapper {
    public static RoleResponseDto toDto(Role role) {
        if (role == null) {
            return null;
        }
        return new RoleResponseDto(role.getId(), role.getName());
    }
}
