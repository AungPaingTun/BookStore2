package com.example.bookstore.service;


import com.example.bookstore.entities.*;
import com.example.bookstore.repo.BookRepo;
import com.example.bookstore.repo.CustomerBookOrderRepo;
import com.example.bookstore.repo.CustomerRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerService {


    @Autowired
    private CustomerBookOrderRepo customerBookOrderRepo;

    @Autowired
    private CustomerRepo customerRepo;


    @Autowired
    private BookRepo bookRepo;


    public void saveCustomerBookOrder(Customer customer, List<BookDto> bookDtoList){
        CustomerBookOrder customerBookOrder= new CustomerBookOrder();
        customerBookOrder.setCustomer(customer);
        customerBookOrder.setTotalAmount(totalPrices(bookDtoList));
        customerBookOrder.setOrderCode(generateCode(customer));
        customerBookOrder.setOrderStatus(OrderStatus.PLACED);

        List<Book> books = bookDtoList.stream()
                        .map(bookDto -> {
                            Book book = convertToBook(bookDto);
                            if(book.getId() !=null){
                                return bookRepo.findById(book.getId()).orElse(book);
                            }
                            return book;
                        })
                                .collect(Collectors.toList());
        customerBookOrder.setBooks(books);
        customerBookOrderRepo.save(customerBookOrder);
    }

    private Book convertToBook(BookDto bookDto){
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(book.getTitle());
        book.setPrice(book.getPrice());
        return book;
    }

    private String generateCode(Customer customer){

        Random random= new Random();
        int code = random.nextInt(100000)+100000;
        return customer.getName()+code;
    }


    public int totalPrices(List<BookDto> bookDtos){
        double totalInDollars = bookDtos.stream().map(BookDto::getPrice).mapToDouble(d -> d).sum();
        return (int) (totalInDollars*100);

    }

    public List<CustomerBookOrder> findAllOrders(){
        return customerBookOrderRepo.findAll();
    }

    public List<CustomerBookOrder> findPlaceOrder(){
        return customerBookOrderRepo.findCustomerBookOrderByOrderStatus(OrderStatus.PLACED);
    }

    public void updateOrderStatus(Long orderId,OrderStatus newStatus){
        CustomerBookOrder order = customerBookOrderRepo.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.setOrderStatus(newStatus);
        customerBookOrderRepo.save(order);
    }

    public void deleteOrder(Long orderId){
        customerBookOrderRepo.deleteById(orderId);
    }
}
