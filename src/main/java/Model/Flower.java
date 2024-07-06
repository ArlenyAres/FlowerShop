package Model;

public class Flower extends Product {

    // Attributes
    private String color;

    // Constructor
    public Flower (String name, String id, double price, String color) {
        super(name, id, price);
        this.color = color;
    }

    // Getters
    public String getColor() {
        return color;
    }

    // Setters
    public void setColor(String color) {
        this.color = color;
    }
}
