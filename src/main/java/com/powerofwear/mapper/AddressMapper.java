package com.powerofwear.mapper;

import com.powerofwear.entity.Address;
import com.powerofwear.entity.User;
import com.powerofwear.dto.address.AddressCreateDto;
import com.powerofwear.dto.address.AddressResponseDto;
import com.powerofwear.dto.address.AddressUpdateDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address fromCreateDto(AddressCreateDto dto) {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        address.setDistrict(dto.getDistrict());
        address.setStreet(dto.getStreet());
        address.setHouseNumber(dto.getHouseNumber());
        address.setUser(userIdToUser(dto.getUserId()));
        return address;
    }

    public void updateEntityFromDto(AddressUpdateDto dto, Address address) {
        if (dto == null || address == null) {
            return;
        }
        if (dto.getDistrict() != null) {
            address.setDistrict(dto.getDistrict());
        }
        if (dto.getStreet() != null) {
            address.setStreet(dto.getStreet());
        }
        if (dto.getHouseNumber() != null) {
            address.setHouseNumber(dto.getHouseNumber());
        }
    }

    public AddressResponseDto toResponseDto(Address address) {
        if (address == null) {
            return null;
        }
        return AddressResponseDto.builder()
                .id(address.getId())

                .userId(address.getUser() != null ? address.getUser().getId() : null)
                .userFullName(address.getUser() != null ? address.getUser().getFirstName() : null)
                .fullAddress(buildFullAddress(address))
                .build();
    }

    protected User userIdToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }

    protected String buildFullAddress(Address address) {
        if (address == null) return null;

        StringBuilder sb = new StringBuilder();

        if (address.getDistrict() != null && !address.getDistrict().isBlank()) {
            sb.append(address.getDistrict());
        }
        if (address.getStreet() != null && !address.getStreet().isBlank()) {
            sb.append(", ").append(address.getStreet());
        }
        if (address.getHouseNumber() != null && !address.getHouseNumber().isBlank()) {
            sb.append(", house ").append(address.getHouseNumber());
        }

        String result = sb.toString();
        return result.replaceFirst("^, ", "").trim();
    }
}
