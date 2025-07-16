package com.ecommerce.controller;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private OrderRepository orderRepo;

    @GetMapping("/checkout")
    public String checkoutForm(Model model) {
        double total = cartRepo.findAll().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("total", total);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@RequestParam String name, @RequestParam String address) {
        double total = cartRepo.findAll().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        OrderItem order = new OrderItem();
        order.setCustomerName(name);
        order.setAddress(address);
        order.setTotalPrice(total);
        order.setOrderDate(new Date());

        orderRepo.save(order);
        cartRepo.deleteAll();
        return "redirect:/order/success";
    }

    @GetMapping("/order/success")
    public String successPage() {
        return "order_success";
    }
}
