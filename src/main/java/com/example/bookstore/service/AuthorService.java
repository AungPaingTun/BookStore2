package com.example.bookstore.service;


import com.example.bookstore.entities.Author;
import com.example.bookstore.repo.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {


    @Autowired
    private AuthorRepo authorRepo;


    public void saveAuthor(Author author){
        authorRepo.save(author);
    }


    public List<Author> findAllAuthor(){
        return authorRepo.findAll();

    }
}
