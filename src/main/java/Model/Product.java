package Model;

import java.util.UUID;

public class Product {

    // Attributes
    private String productId;
    private String name;
    private double price;

    // Constructor
    public Product(String name, double price) {
        this.productId = String.valueOf(UUID.randomUUID());
        this.name = name;
        this.price = price;
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
