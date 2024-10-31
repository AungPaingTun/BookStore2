package com.example.bookstore.repo;

import com.example.bookstore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {

}
