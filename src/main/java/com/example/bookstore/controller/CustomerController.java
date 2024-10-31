package com.example.bookstore.controller;


import com.example.bookstore.entities.BookDto;
import com.example.bookstore.entities.Customer;
import com.example.bookstore.service.*;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class CustomerController {


    @Autowired
    private CartService cartService;

    @Autowired
    private FileUploadService fileUploadService;


    @Autowired
    private CustomerService customerService;


    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EmailService emailService;

    private static final String MESSAGE_ATTR = "message";

    @PostMapping("/customer/save")
    public String saveCustomerOrder(@ModelAttribute("customer") Customer customer, @RequestParam("receiptFile") MultipartFile receiptFile,
                                    BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "checkOut";
        }
        List<BookDto> cartItems = cartService.listCart();
        // Handle QR code payment receipt upload
        if ("QR Code".equals(customer.getPaymentMethod())) {
            if (!receiptFile.isEmpty()) {
                try {
                    String receiptPath = fileUploadService.uploadFile(receiptFile);
                    customer.setPaymentReceiptPath(receiptPath);
                } catch (IOException e) {
                    model.addAttribute("error", "Failed to upload receipt.Please Try Again.");
                    return "checkOut";
                }
            } else {
                model.addAttribute("error", "Please upload a receipt for QR code Payment");
                return "checkOut";
            }
        }

        // Save the order details in the database
        customerService.saveCustomerBookOrder(customer, cartItems);
        String emailSubject = "Order Confirmation- Thank You for Your Purchase!";
        String emailBody = """
                Dear Customer
                    Thank you for your purchase. Here is your order details;
                    Name:
                    Phone:
                    Address:
                    Email:
                    We will send your order as soon as possible.
                    Best regards.
                    BookStore
                """;
        emailService.sendConfirmationEmail(customer.getEmail(), emailSubject, emailBody);


        String paymentMethod = customer.getPaymentMethod();
        switch (paymentMethod) {
            case "QR Code":
                model.addAttribute(MESSAGE_ATTR, "Please scan QR Code to complete your payment");
                break;
            case "Card":
                int totalAmount = customerService.totalPrices(cartItems);
                PaymentIntent paymentIntent = paymentService.createPaymentIntent(totalAmount);
                model.addAttribute("paymentIntentClientSecret", paymentIntent.getClientSecret());
                model.addAttribute(MESSAGE_ATTR, "Please confirm your card payment.");
                break;
            case "Cash on Delivery":
                model.addAttribute("message", "Your order will be paid upon delivery");
                break;
            default:
                model.addAttribute("error", "Please select a valid payment method");
                return "checkOut";
        }

        cartService.clearCart();
        model.addAttribute("success", "Your Order has been successfully placed");
        return "success";
    }


    @GetMapping("/customer/checkout")
    public String checkOut(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("cartItems", cartService.listCart());
        try {
            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                    .setAmount(1000L)
                    .setCurrency("usd")
                    .build();
            PaymentIntent paymentIntent = PaymentIntent.create(createParams);
            String clientSecret = paymentIntent.getClientSecret();
            model.addAttribute("paymentIntentClientSecret", clientSecret);
        } catch (StripeException e) {
            model.addAttribute("error", "Error creating payment intent:" + e.getMessage());
        }
        return "checkOut";
    }
}
