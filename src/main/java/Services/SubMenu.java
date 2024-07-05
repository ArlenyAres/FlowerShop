package Services;

import Model.Decoration;
import Model.Flower;
import Model.Product;
import Model.Tree;

import static Services.Input.*;

public class SubMenu {
    public static Product createProduct() {
        Product product = null;
        int option = -1;
        int type = 0;
        String name = "";
        String color = "";
        double price = 0d;
        double height = 0d;
        boolean working = true;

        do {
            System.out.println("Products : \n " +
                    "1. Decoration\n " +
                    "2. Flower\n " +
                    "3. Tree\n " +
                    "0. Exit");
            option = readInt("What type of product would like to add?");

            switch (option) {
                case 1:
                    product = createDecoration();
                    working = false;
                    break;
                case 2:
                    product = createFlower();
                    working = false;
                    break;
                case 3:
                    product = createTree();
                    working = false;
                    break;
                case 0:
                    working = false;
                    break;
                default:
                    System.out.println("Invalid option, try again");
            }

        } while (working);
        return product;
    }

    public static Decoration createDecoration(){
        Decoration product = null;
        int type = 0;
        String name = "";
        double price = 0d;

        name = readString("Name of the decoration? ");
        price = readDouble("How much is it? ");
        type = readInt("What type of decoration?\n 1. WOOD\n 2. PLASTIC");
        if (type == 1){
            product = new Decoration(name, price, Decoration.DecorationType.WOOD);
        } else if (type == 2) {
            product = new Decoration(name, price, Decoration.DecorationType.PLASTIC);
        } else {
            System.out.println("Unable to create the product, please verify the product info! ");
        }
        return product;
    }

    public static Flower createFlower(){
        Flower product;
        String name = "";
        String color = "";
        double price = 0d;

        name = readString("Name of the decoration? ");
        price = readDouble("How much is it? ");
        color = readString("Color of the decoration?");
        product = new Flower(name,price,color);

        return product;
    }

    public static Tree createTree(){
        Tree product;
        String name = "";
        double price = 0d;
        double height = 0d;

        name = readString("Name of the decoration? ");
        price = readDouble("How much is it? ");
        height = readDouble("What is the height of the tree");
        product = new Tree(name, price, height);

        return product;
    }
}