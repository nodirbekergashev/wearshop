package com.powerofwear.controller;

import com.powerofwear.dto.product.ProductResponseDto;
import com.powerofwear.service.CategoryService;
import com.powerofwear.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        ProductResponseDto product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/detail";
    }

    @GetMapping("/categories/{id}")
    public String getProductsByCategory(@PathVariable Long id,
                                        @PageableDefault(size = 12) Pageable pageable,
                                        Model model) {
        Page<ProductResponseDto> products = productService.getProductsByCategory(id, pageable);
        model.addAttribute("products", products);
        model.addAttribute("category", categoryService.getById(id));
        return "category/products";
    }
}
