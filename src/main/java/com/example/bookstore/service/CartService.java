package com.example.bookstore.service;

import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.BookDto;
import com.example.bookstore.entities.Cart;
import com.example.bookstore.repo.AuthorRepo;
import com.example.bookstore.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {


    @Autowired
    private Cart cart;


    @Autowired
    private CategoryRepo categoryRepo;


    @Autowired
    private AuthorRepo authorRepo;


    public BookDto toDto(Book book){
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getPrice(),
                book.getYearPublished(),
                book.getDescription(),
                book.getImageUrl(),
                book.getCategory().getId(),
                book.getAuthor().getId()
        );
    }


    public void addToCart(Book book){
        cart.addToCart(toDto(book));
    }

    public List<BookDto> listCart(){
        return cart.getBookDtos();
    }

    public int cartSize(){
        return cart.cartSize();
    }

    public void remove(BookDto bookDto){
        cart.removeBookFromCart(bookDto);
    }

    public void clearCart(){
        cart.clearCart();
    }


}
