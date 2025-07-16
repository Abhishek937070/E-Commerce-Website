package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/login")
    public String adminLogin() {
        return "admin_login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session) {
        if (username.equals("admin") && password.equals("admin123")) {
            session.setAttribute("admin", true);
            return "redirect:/admin/products";
        }
        return "admin_login";
    }

    @GetMapping("/products")
    public String viewProducts(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/admin/login";
        model.addAttribute("products", productRepo.findAll());
        return "admin_products";
    }

    @GetMapping("/add-product")
    public String addProductPage() {
        return "add_product";
    }

  @PostMapping("/add-product")
public String saveProduct(@RequestParam String name,
                          @RequestParam String description,
                          @RequestParam double price,
                          @RequestParam MultipartFile image) throws IOException {

    // Create upload directory if it doesn't exist
    String uploadDir = System.getProperty("user.dir") + "/uploads";
    File uploadPath = new File(uploadDir);
    if (!uploadPath.exists()) {
        uploadPath.mkdirs(); // create /uploads folder
    }

    // Save file to /uploads
    String imageName = image.getOriginalFilename();
    image.transferTo(new File(uploadDir + "/" + imageName));

    // Save product in DB
    Product p = new Product();
    p.setName(name);
    p.setDescription(description);
    p.setPrice(price);
    p.setImageName(imageName);
    productRepo.save(p);

    return "redirect:/admin/products";
}


    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Optional<Product> product = productRepo.findById(id);
        product.ifPresent(p -> model.addAttribute("product", p));
        return "edit_product";
    }

    @PostMapping("/edit-product")
    public String updateProduct(@RequestParam Long id,
                                @RequestParam String name,
                                @RequestParam String description,
                                @RequestParam double price,
                                @RequestParam MultipartFile image) throws IOException {

        Product p = productRepo.findById(id).orElseThrow();
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);

        if (!image.isEmpty()) {
            String imageName = image.getOriginalFilename();
            image.transferTo(new File("src/main/resources/static/images/" + imageName));
            p.setImageName(imageName);
        }

        productRepo.save(p);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepo.deleteById(id);
        return "redirect:/admin/products";
    }
    @GetMapping("/logout")
public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/admin/login";
}

}
