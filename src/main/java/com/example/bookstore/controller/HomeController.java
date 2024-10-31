package com.example.bookstore.controller;

import com.example.bookstore.entities.Book;
import com.example.bookstore.service.AuthorService;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private BookService bookService;


    @GetMapping(value={"/","/home"})
   public String index(Model model){

       return "home";
   }

    @ModelAttribute("allbooks")
    public List<Book> showAllBook(){
        return bookService.findAllBook();
    }
    @GetMapping("/shop/show-all-book")
    public String listBooks(){
        return "list-books";
    }
}
