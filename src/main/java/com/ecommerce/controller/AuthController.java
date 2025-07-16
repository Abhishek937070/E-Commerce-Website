package com.ecommerce.controller;

import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Show login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Process login with DB check
    @PostMapping("/do-login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user.getName());
            return "redirect:/user/home";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    // Show registration form
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // Process registration and save to DB
    @PostMapping("/register")
    public String processRegistration(@RequestParam String name,
                                      @RequestParam String email,
                                      @RequestParam String password,
                                      Model model) {

        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password); // 🔒 store securely in production
        userRepository.save(user);

        return "redirect:/login";
    }
}
