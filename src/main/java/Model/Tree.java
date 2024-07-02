package Model;

public class Tree extends Product{

    // Attributes
    private double height;

    // Constructor
    public Tree(String name, double price, double height) {
        super(name, price);
        this.height = height;
    }

    // Getters
    public double getHeight(){
        return height;
    }

    // Setters
    public void setHeight(double height) {
        this.height = height;
    }
}
