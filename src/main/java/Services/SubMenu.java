package Services;

import Model.*;
import Exception.InsufficientStockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import static Services.Input.*;

public class SubMenu {

    private static final Logger logger = LoggerFactory.getLogger(SubMenu.class);

    public static void createProduct(FlowerShop shop, MongoDBService mongoDBService) {
        int quantity = readInt("How many products? \n");
        boolean working = true;

        do {
            System.out.println("What type of product would you like to add? : \n " +
                    "1. Decoration\n " +
                    "2. Flower\n " +
                    "3. Tree\n " +
                    "0. Exit");
            int option = readInt("");

            switch (option) {
                case 1:
                    createDecoration(shop, quantity, mongoDBService);
                    working = false;
                    break;
                case 2:
                    createFlower(shop, quantity, mongoDBService);
                    working = false;
                    break;
                case 3:
                    createTree(shop, quantity, mongoDBService);
                    working = false;
                    break;
                case 0:
                    System.out.println("No products added to the stock");
                    working = false;
                    break;
                default:
                    System.out.println("Invalid option, try again");
            }
        } while (working);
    }

    public static void createDecoration(FlowerShop shop, int quantity, MongoDBService mongoDBService) {
        Decoration product;
        int type = 0;
        String productName = readString("Name of the decoration? ");
        double price = readDouble("How much is it?\n");
        type = readInt("What type of decoration?\n 1. WOOD\n 2. PLASTIC\n");

        if (type == 1) {
            product = new Decoration(productName, price, Decoration.DecorationType.WOOD);
        } else if (type == 2) {
            product = new Decoration(productName, price, Decoration.DecorationType.PLASTIC);
        } else {
            System.out.println("Unable to create the product, please verify the product info! ");
            return;
        }

        shop.getStockFromRepository().addProduct(product, quantity);
        mongoDBService.updateFlowerShop(shop); // Update the flower shop in MongoDB after adding a product
        System.out.println("The products were added to the stock and saved to MongoDB.");
    }

    public static void createFlower(FlowerShop shop, int quantity, MongoDBService mongoDBService) {
        Flower product;
        String name = readString("Name of the flower? ");
        double price = readDouble("How much is it? \n");
        String color = readString("Color of the flower?");

        product = new Flower(name, price, color);
        shop.getStockFromRepository().addProduct(product, quantity);
        mongoDBService.updateFlowerShop(shop); // Update the flower shop in MongoDB after adding a product
        System.out.println("The products were added to the stock and saved to MongoDB.");
    }

    public static void createTree(FlowerShop shop, int quantity, MongoDBService mongoDBService) {
        Tree product;
        String name = readString("Name of the tree? ");
        double price = readDouble("How much is it? \n");
        double height = readDouble("What is the height of the tree\n");

        product = new Tree(name, price, height);
        shop.getStockFromRepository().addProduct(product, quantity);
        mongoDBService.updateFlowerShop(shop); // Update the flower shop in MongoDB after adding a product
        System.out.println("The products were added to the stock and saved to MongoDB.");
    }

    public static Purchase createPurchase(FlowerShop shop, MongoDBService mongoDBService) {
        Purchase purchase = new Purchase(shop, shop.getStockFromRepository().getStock());
        Product product = null;
        String productName;
        boolean buying = true;
        int option;
        int quantity;

        do {
            System.out.println("What do you want to do?\n " +
                    "1. Add product to the cart\n " +
                    "2. Remove product from the cart\n " +
                    "3. Get total price of the purchase\n " +
                    "0. Exit");
            option = readInt("");

            switch (option) {
                case 1:
                    productName = readString("Enter the name of the product : ");
                    product = findProductByName(shop, productName);
                    if (product != null) {
                        quantity = readInt("How many products?\n");
                        try {
                            purchase.addProductInPurchaseCart(product, quantity);
                        } catch (InsufficientStockException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 2:
                    productName = readString("Enter the name of the product : ");
                    product = findProductByName(shop, productName);
                    if (product != null) {
                        quantity = readInt("How many products?\n");
                        purchase.removeProductFromPurchaseCart(product, quantity);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 3:
                    System.out.println("Total of the purchase : " + purchase.calculateTotalPrice() + "\n");
                    break;
                case 0:
                    System.out.println("Good Bye!\n");
                    Ticket ticket = new Ticket(purchase);
                    ticket.createTicket();
                    buying = false;
                    break;
                default:
                    System.out.println("Invalid option, try again\n");
            }
        } while (buying);

        mongoDBService.updateFlowerShop(shop); // Update the flower shop in MongoDB after the purchase
        return purchase;
    }

    private static Product findProductByName(FlowerShop shop, String productName) {
        for (Product prod : shop.getStockFromRepository().getStock().keySet()) {
            if (prod.getName().equalsIgnoreCase(productName)) {
                return prod;
            }
        }
        return null;
    }
}

