package Model;

import java.util.Objects;

public class Product {

    // Attributes
    private String productID;
    private String name;
    private double price;

    // Constructor
    public Product(String name, String productID, double price) {
        this.name = name;
        this.productID = productID;
        this.price = price;
    }

    // Getters
    public String getProductID() {
        return productID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productID, product.productID) &&
                Objects.equals(name, product.name) &&
                Double.compare(product.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, productID, price);
    }
}
