package com.example.bookstore.repo;

import com.example.bookstore.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author,Long> {

}
