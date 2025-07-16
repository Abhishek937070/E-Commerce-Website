package com.ecommerce.controller;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CartRepository cartRepo;

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id) {
        Product product = productRepo.findById(id).orElseThrow();

        CartItem item = new CartItem();
        item.setProductId(product.getId());
        item.setName(product.getName());
        item.setPrice(product.getPrice());
        item.setQuantity(1);
        item.setImageName(product.getImageName());

        cartRepo.save(item);
        return "redirect:/cart/view";
    }

    @GetMapping("/view")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartRepo.findAll());

        double total = cartRepo.findAll().stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();
        model.addAttribute("total", total);

        return "cart";
    }

    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable Long id) {
        cartRepo.deleteById(id);
        return "redirect:/cart/view";
    }
}
