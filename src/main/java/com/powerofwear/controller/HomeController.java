//package com.powerofwear.controller;
//
//import com.powerofwear.service.ProductService;
//import com.powerofwear.service.CategoryService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.Locale;
//
//@Controller
//public class HomeController {
//
//    private final ProductService productService;
//    private final CategoryService categoryService;
//
//    public HomeController(ProductService productService,
//                          CategoryService categoryService) {
//        this.productService = productService;
//        this.categoryService = categoryService;
//    }
//
//    @GetMapping("/")
//    public String home(Model model, Locale locale) {
//        model.addAttribute("featuredProducts",
//                productService.getFeaturedProducts(locale.getLanguage()));
//        model.addAttribute("categories",
////                categoryService.getAll(locale.getLanguage()));
//        return "home/index";
//    }
//
//    @GetMapping("/products")
//    public String products(Model model, Locale locale) {
//        model.addAttribute("products",
//                productService.getAllProducts(locale.getLanguage()));
//        model.addAttribute("categories",
//                categoryService.getAllCategories(locale.getLanguage()));
//        return "home/products";
//    }
//
//    @GetMapping("/products/category/{id}")
//    public String productsByCategory(@PathVariable Long id,
//                                     Model model,
//                                     Locale locale) {
//        model.addAttribute("products",
//                productService.getProductsByCategory(id, locale.getLanguage()));
//        model.addAttribute("category",
//                categoryService.getCategoryById(id, locale.getLanguage()));
//        return "home/products";
//    }
//
//    @GetMapping("/product/{id}")
//    public String productDetail(@PathVariable Long id,
//                                Model model,
//                                Locale locale) {
//        model.addAttribute("product",
//                productService.getProductById(id, locale.getLanguage()));
//        return "home/product-detail";
//    }
//}