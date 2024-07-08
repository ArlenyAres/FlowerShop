package Services;

import Model.*;
import static Services.Input.*;
import static Services.Menu.findShop;
import Exception.InsufficientStockException;

public class SubMenu {
    public static void createProduct(FlowerShopManager admin) {
        String shopName = readString("What is the name of the flower shop?");
        int quantity = readInt("How many products? \n");
        boolean working = true;
        while (working) {
            System.out.println("Products : \n " +
                    "1. Decoration\n " +
                    "2. Flower\n " +
                    "3. Tree\n " +
                    "0. Exit");
            int option = readInt("What type of product would you like to add?\n");

            switch (option) {
                case 1:
                    createDecoration(shopName, quantity, admin);
                    working = false;
                    break;
                case 2:
                    createFlower(shopName, quantity, admin);
                    working = false;
                    break;
                case 3:
                    createTree(shopName, quantity, admin);
                    working = false;
                    break;
                case 0:
                    working = false;
                    break;
                default:
                    System.out.println("Invalid option, try again");
            }
        }
    }

    public static void createDecoration(String shopName, int quantity, FlowerShopManager admin){
        Decoration product;
        FlowerShop shop = findShop(admin, shopName);
        int type = 0;
        String productName = "";
        double price = 0d;

        productName = readString("Name of the decoration? ");
        price = readDouble("How much is it?\n");
        type = readInt("What type of decoration?\n 1. WOOD\n 2. PLASTIC\n");

        if (type == 1){
            product = new Decoration(productName, price, Decoration.DecorationType.WOOD);
            shop.getStockFromRepository().addProduct(product, quantity);
        } else if (type == 2) {
            product = new Decoration(productName, price, Decoration.DecorationType.PLASTIC);
            shop.getStockFromRepository().addProduct(product, quantity);
        } else {
            System.out.println("Unable to create the product, please verify the product info! ");
        }
    }

    public static void createFlower(String shopName, int quantity, FlowerShopManager admin){
        Flower product;
        FlowerShop shop = findShop(admin, shopName);
        String name = "";
        String color = "";
        double price = 0d;

        name = readString("Name of the flower? ");
        price = readDouble("How much is it? \n");
        color = readString("Color of the flower?");

        product = new Flower(name,price,color);
        shop.getStockFromRepository().addProduct(product, quantity);
    }

    public static void createTree(String shopName, int quantity, FlowerShopManager admin){
        Tree product;
        FlowerShop shop = findShop(admin, shopName);
        String name = "";
        double price = 0d;
        double height = 0d;

        name = readString("Name of the tree? ");
        price = readDouble("How much is it? \n");
        height = readDouble("What is the height of the tree\n");

        product = new Tree(name, price, height);
        shop.getStockFromRepository().addProduct(product, quantity);
    }

    public static Purchase createPurchase(FlowerShop shop) {
        Purchase purchase = new Purchase(shop,shop.getStockFromRepository().getStock());
        Product product = null;
        String productName = "";
        boolean buying = true;
        int option = -1;
        int quantity = 0;

        do {
            System.out.println("What do you want to do?\n " +
                    "1. Add product to the cart\n " +
                    "2. Remove product from the cart\n " +
                    "3. Get total price of the purchase\n " +
                    "0. Exit");
            option = readInt("");

            switch (option){
                case 1 :
                    productName = readString("Enter the name of the product : ");
                    for (Product prod : shop.getStockFromRepository().getStock().keySet()){
                        if (prod.getName().equalsIgnoreCase(productName)){
                            product = prod;
                        }
                    }
                    quantity = readInt("How many products?\n");
                    try {
                        purchase.addProductInPurchaseCart(product,quantity);
                    } catch (InsufficientStockException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2 :
                    productName = readString("Enter the name of the product : ");
                    for (Product prod : shop.getStockFromRepository().getStock().keySet()){
                        if (prod.getName().equalsIgnoreCase(productName)){
                            product = prod;
                        }
                    }
                    quantity = readInt("How many products?\n");
                    purchase.removeProductFromPurchaseCart(product,quantity);
                    break;
                case 3 :
                    System.out.println("Total of the purchase : " + purchase.calculateTotalPrice() + "\n");
                    break;
                case 0 :
                    System.out.println("Good Bye!\n");
                    buying = false;
                    break;
                default:
                    System.out.println("Invalid option, try again\n");
            }
        } while (buying);
        return purchase;
    }

}