package com.powerofwear.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.powerofwear.dto.user.UserCreateDto;
import com.powerofwear.dto.user.UserResponseDto;
import com.powerofwear.dto.user.UserUpdateDto;
import com.powerofwear.entity.Role;
import com.powerofwear.entity.User;
import com.powerofwear.exceptions.ResourceNotFoundException;
import com.powerofwear.mapper.UserMapper;
import com.powerofwear.repository.RoleRepository;
import com.powerofwear.repository.UserRepository;
import com.powerofwear.service.CartService;
import com.powerofwear.service.UserService;
import com.powerofwear.validator.UserValidator;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserValidator validator;
    private final CartService cartService;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return findByUsername(username);
    }

    @Override
    public UserResponseDto save(UserCreateDto dto) {
        validator.validateOnCreate(dto);
        User user = mapper.fromCreateDto(dto);
        Role role = roleRepository.findRoleByName("USER").orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        repository.save(user);
        cartService.createCart(user.getId());
        return mapper.toDto(user);
    }

    @Override
    public UserResponseDto update(Long id, UserUpdateDto dto) {
        validator.validateOnUpdate(dto);
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + id));

        mapper.fromUpdateDto(user, dto);
        repository.save(user);

        return mapper.toDto(user);
    }

    @Override
    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + id));

        user.softDelete();
        repository.save(user);
    }

    @Override
    public UserResponseDto getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + id));
        return mapper.toDto(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponseDto> getAll(Pageable pageable) {
        Page<User> userPage = repository.findAll(pageable);

        return userPage.map(mapper::toDto);
    }

    @Override
    public User findByUsername(String username) {
        return
                repository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + id));
    }

    @Override
    public UserResponseDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        String username = authentication.getName();
        User currentUser = findByUsername(username);
        return mapper.toDto(currentUser);
    }


}
