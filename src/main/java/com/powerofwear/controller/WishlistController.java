package com.powerofwear.controller;

import com.powerofwear.dto.wishlist.WishlistRequestDto;
import com.powerofwear.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/{userId}")
    public String viewWishlist(@PathVariable Long userId, Model model) {
        model.addAttribute("wishlist", wishlistService.getWishlistByUserId(userId));
        model.addAttribute("userId", userId);
        return "wishlist/view";
    }


    @PostMapping("/add")
    public String addToWishlist(@ModelAttribute WishlistRequestDto requestDto) {
        wishlistService.addProductToWishlist(requestDto);
        return "redirect:/wishlist/" + requestDto.getUserId();
    }

    @PostMapping("/remove")
    public String removeFromWishlist(@ModelAttribute WishlistRequestDto requestDto) {
        wishlistService.removeProductFromWishlist(requestDto);
        return "redirect:/wishlist/" + requestDto.getUserId();
    }
}
