package Model;

public class Decoration extends Product {


    DecorationType type;

    public Decoration (String name, double price, DecorationType type){
        super(name, price);
        this.type = type;
    }

    public enum DecorationType {
        WOOD, PLASTIC
    }

    public DecorationType getType() {
        return type;
    }

    public void setType(DecorationType type) {
        this.type = type;
    }
}
