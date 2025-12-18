package com.powerofwear.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.powerofwear.mapper.RoleMapper;
import com.powerofwear.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleMapper roleMapper;

}