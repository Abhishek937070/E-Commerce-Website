package com.ecommerce.controller;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return "redirect:/user/home";

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        // check if item already in cart
        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProductId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            cart.add(new CartItem(product.getId(), product.getName(), product.getPrice(), product.getImageName(), 1));
        }

        session.setAttribute("cart", cart);
        return "redirect:/user/home";
    }

    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        model.addAttribute("cartItems", cart != null ? cart : new ArrayList<>());
        return "cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getProductId().equals(productId));
        }
        return "redirect:/cart";
    }
}
