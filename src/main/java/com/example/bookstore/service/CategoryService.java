package com.example.bookstore.service;


import com.example.bookstore.entities.Category;
import com.example.bookstore.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public void saveCategory(Category category){
        categoryRepo.save(category);
    }



    public List<Category> findAllCategories(){
        return categoryRepo.findAll();
    }
}
