package com.example.bookstore.repo;

import com.example.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book,Long>{
}
