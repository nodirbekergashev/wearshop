package com.powerofwear.controller;

import com.powerofwear.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final ProductService productService;

    @GetMapping("/search")
    public String search(@RequestParam("q") String query, Model model, Pageable pageable) {
        model.addAttribute("products", productService.searchProducts(query, pageable));
        model.addAttribute("query", query);
        return "search/results";
    }
}
