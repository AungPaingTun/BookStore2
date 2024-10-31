package com.example.bookstore.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private double price;
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private LocalDate yearPublished;
    private String description;
    private String imageUrl;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Author author;
    @Transient
    private Long categoryId;
    @Transient
    private Long authorId;

    @ManyToMany(mappedBy = "books")
    private List<CustomerBookOrder> customerBookOrders = new ArrayList<>();

}
