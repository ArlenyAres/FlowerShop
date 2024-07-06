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
        FlowerShop shop = findShop(admin, shopName);
        String productName = readString("Name of the decoration? ");
        double price = readDouble("How much is it?\n");
        int type = readInt("What type of decoration?\n 1. WOOD\n 2. PLASTIC\n");
        Decoration.DecorationType decorationType = (type == 1) ? Decoration.DecorationType.WOOD : Decoration.DecorationType.PLASTIC;

        for (int i = 0; i < quantity; i++) {
            String id = productName + "-" + decorationType + "-" + price;  // Generate consistent ID based on product properties
            Decoration product = new Decoration(productName, id, price, decorationType);
            shop.getStockFromRepository().addProduct(product, 1);
        }
    }

    public static void createFlower(String shopName, int quantity, FlowerShopManager admin){
        FlowerShop shop = findShop(admin, shopName);
        String name = readString("Name of the flower? ");
        double price = readDouble("How much is it? \n");
        String color = readString("Color of the flower?");

        for (int i = 0; i < quantity; i++) {
            String id = name + "-" + color + "-" + price;  // Generate consistent ID based on product properties
            Flower product = new Flower(name, id, price, color);
            shop.getStockFromRepository().addProduct(product, 1);
        }
    }

    public static void createTree(String shopName, int quantity, FlowerShopManager admin){
        FlowerShop shop = findShop(admin, shopName);
        String name = readString("Name of the tree? ");
        double price = readDouble("How much is it? \n");
        double height = readDouble("What is the height of the tree\n");

        for (int i = 0; i < quantity; i++) {
            String id = name + "-" + height + "-" + price;  // Generate consistent ID based on product properties
            Tree product = new Tree(name, id, price, height);
            shop.getStockFromRepository().addProduct(product, 1);
        }
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