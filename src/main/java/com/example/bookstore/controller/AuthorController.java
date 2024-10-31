package com.example.bookstore.controller;


import com.example.bookstore.entities.Author;
import com.example.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @PostMapping("/save")
    public String saveAuthor(Author author, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "home";
        }
        authorService.saveAuthor(author);
        return "redirect:/admin/dashboard";
    }


    @GetMapping("findAll")
    public String findAllAuthor(Model model){
        model.addAttribute("authors",authorService.findAllAuthor());
        return "list-author";
    }
}
