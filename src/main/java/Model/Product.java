package Model;

import org.bson.types.ObjectId;

public class Product {

    // Attributes

    private int id;
    private static int nextId = 1;
    private String name;
    private double price;

    // Constructor
    public Product(String name, double price) {
        this.id = nextId++;
        this.name = name;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
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
