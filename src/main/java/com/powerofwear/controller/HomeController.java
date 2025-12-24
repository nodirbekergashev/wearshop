package com.powerofwear.controller;

import com.powerofwear.service.ProductService;
import com.powerofwear.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model, Locale locale) {
        model.addAttribute("products",
                productService.getActiveProducts(13));
        return "home";
    }

}