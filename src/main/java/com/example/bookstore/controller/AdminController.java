package com.example.bookstore.controller;


import com.example.bookstore.entities.Author;
import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Category;
import com.example.bookstore.entities.OrderStatus;
import com.example.bookstore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;


    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;


    @GetMapping("/dashboard")
    public String adminHomePage(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("author", new Author());
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAllAuthor());
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("books", bookService.findAllBook());
        model.addAttribute("placeOrders", customerService.findAllOrders());
        model.addAttribute("allOrders", customerService.findAllOrders());
        return "dashboard";
    }

    @PostMapping("/order/update-status/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus newStatus, RedirectAttributes redirectAttributes) {
        try {
            customerService.updateOrderStatus(id, newStatus);
            redirectAttributes.addFlashAttribute("successMessage", "Order status update successfully.");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error message", "Failed to update order status!");
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteOrder(id);
            redirectAttributes.addFlashAttribute("successMessage", "Order delete successfully.");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error message", "Failed to delete order!");
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/order/findAll")
    public String findAllOrders(Model model) {
        model.addAttribute("allOrders", customerService.findAllOrders());
        return "allOrdersTable";

    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String username, @RequestParam String newPassword) {
        userService.changePassword(username, newPassword);

        return ResponseEntity.ok("Password changed successfully");
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUsers(@RequestParam String username, @RequestParam String password, @RequestParam String[] roles) {
        userService.createUser(username, password, roles);
        return ResponseEntity.ok("User Created Successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String username) {
        String defaultPassword = "defaultPassword123";
        userService.resetPassword(username, defaultPassword);
        return ResponseEntity.ok("Password reset successfully");
    }

}
