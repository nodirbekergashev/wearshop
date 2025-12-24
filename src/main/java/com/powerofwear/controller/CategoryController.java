package com.powerofwear.controller;

import com.powerofwear.dto.category.CategoryCreateDto;
import com.powerofwear.dto.category.CategoryResponseDto;
import com.powerofwear.dto.category.CategoryUpdateDto;
import com.powerofwear.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ModelAttribute("/allCategories")
    public List<CategoryResponseDto> allCategories() {
        return categoryService.getAll();
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryCreateDto());
        return "category/create"; // Separate page
    }

    @PostMapping("/new")
    public String createCategory(@Valid @ModelAttribute("category") CategoryCreateDto categoryDto,
                                 BindingResult result) {
        if (result.hasErrors()) return "category/create";
        categoryService.save(categoryDto);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        return "category/edit"; // Separate page
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable("id") Long id,
                                 @Valid @ModelAttribute("category") CategoryUpdateDto categoryDto,
                                 BindingResult result) {
        if (result.hasErrors()) return "category/edit";
        categoryService.update(id, categoryDto);
        return "redirect:/categories";
    }
}