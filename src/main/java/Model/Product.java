package Model;

import java.util.Objects;
import java.util.UUID;

public class Product {

    private String productID;
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.productID = UUID.randomUUID().toString();
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

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
