package com.example.bookstore.controller;


import com.example.bookstore.entities.Category;
import com.example.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public String saveCategory(Category category, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "home";
        }
        categoryService.saveCategory(category);
        return "redirect:/admin/dashboard";


    }

    @GetMapping("findAll")
    public String findAllCategories(Model model){
        model.addAttribute("categories",categoryService.findAllCategories());
        return "list-category";
    }
}
