package com.example.bookstore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class CustomerBookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderCode;
    private double totalAmount;
    private OrderStatus orderStatus;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "customer_book_order_items",
            joinColumns = @JoinColumn(name = "customer_book_order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();
}
