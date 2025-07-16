package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/user/home")
    public String userHome(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");

        if (username != null) {
            model.addAttribute("username", username);
            List<Product> products = productRepository.findAll();
            model.addAttribute("products", products);
            return "user_home";
        } else {
            return "redirect:/login";
        }
    }
}
