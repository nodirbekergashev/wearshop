package com.powerofwear.controller;

import com.powerofwear.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class MvcCartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public String viewCart(@PathVariable Long userId, Model model) {
        model.addAttribute("cart", cartService.getCartByUserId(userId));
        return "cart/view";
    }
}
