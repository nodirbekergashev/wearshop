package com.powerofwear.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.powerofwear.service.CartService;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

//    @GetMapping("/count")
//    public Integer getCartCount(Model model, ) {
//        model.addAttribute("cartCount", )
//    }

}