package com.ecommerce.entity;

public class CartItem {
    private Long productId;
    private String name;
    private double price;
    private String imageName;
    private int quantity;

    public CartItem() {
    }

    public CartItem(Long productId, String name, double price, String imageName, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageName = imageName;
        this.quantity = quantity;
    }

    // Getters and Setters

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
