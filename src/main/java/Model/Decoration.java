package Model;

public class Decoration extends Product {

    // Attributes
    DecorationType type;

    // Constructor
    public Decoration (String name, double price, DecorationType type){
        super(name, price);
        this.type = type;
    }

    // Enum
    public enum DecorationType {
        WOOD, PLASTIC
    }

    // Getters
    public DecorationType getType() {
        return type;
    }

    // Setters
    public void setType(DecorationType type) {
        this.type = type;
    }
}
