package Model;

public class Tree extends Product{

    // Attributes
    private double height;

    public Tree(String name, double price, double height) {
        super(name, price);
        this.height = height;
    }
}
