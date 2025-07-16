package com.ecommerce.controller;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model; // ✅✅ Required import

import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @PostMapping("/buy")
    public String buyNow(@RequestParam Long productId, HttpSession session) {
        productRepository.findById(productId).ifPresent(product -> {
            OrderItem order = new OrderItem();
            order.setProductId(product.getId());
            order.setName(product.getName());
            order.setPrice(product.getPrice());
            order.setImageName(product.getImageName());
            order.setQuantity(1);
            orderItemRepository.save(order);
        });
        return "redirect:/orders";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            for (CartItem item : cart) {
                OrderItem order = new OrderItem();
                order.setProductId(item.getProductId());
                order.setName(item.getName());
                order.setPrice(item.getPrice());
                order.setImageName(item.getImageName());
                order.setQuantity(item.getQuantity());
                orderItemRepository.save(order);
            }
            session.removeAttribute("cart");
        }
        return "redirect:/orders";
    }

    @GetMapping("/admin")
    public String adminOrders(Model model) {
        model.addAttribute("orders", orderItemRepository.findAll());
        return "admin_orders";
    }

    @GetMapping("/all")
    public String allOrders(Model model) {
        model.addAttribute("orders", orderItemRepository.findAll());
        return "orders";
    }
}
