package Services;

import Model.FlowerShopManager;
import Model.Product;

import static Services.Input.readInt;
import static Services.Input.readString;
import static Services.SubMenu.createProduct;

public class Menu {

    public static void menu() {
        FlowerShopManager admin = new FlowerShopManager();
        Product product = null;
        int option = -1;
        int quantity = 0;
        String text = "";
        boolean working = true;

        do {
            System.out.println("Welcome, how may I help you?");
            System.out.println("1. Create flower shop\n " +
                    "2.Add product to the stock\n " +
                    "3. Remove product from the stock\n " +
                    "4. Show stock\n " +
                    "5. Show products by quantity\n " +
                    "6. Value of the flower shop\n " +
                    "7. Create ticket\n " +
                    "8. Show history\n " +
                    "9. Money earned from all sales\n " +
                    "0. Exit");
            option = readInt("Enter your option : ");

            switch (option){
                case 1 :
                    text = readString("What it the name of the new flower shop?");
                    admin.createFlorist(text);
                    System.out.println("The new flower shop " + text + " was created");
                    break;
                case 2 :
                    text = readString("What is the name of the flower shop?");
                    product = createProduct();
                    quantity = readInt("how many products?");

                    for (int i = 0; i < admin.getShopList().size(); i++) {
                        if (admin.getShopList().get(i).getName().equalsIgnoreCase(text)){
                            admin.getShopList().get(i).getStockFromRepository().addProduct(product,quantity);
                        }
                    }
                    break;
                case 3 :
                    text = readString("what is the name of the flower shop?");

                    for (int i = 0; i < admin.getShopList().size(); i++) {
                        if (admin.getShopList().get(i).getName().equalsIgnoreCase(text)){
                            text = readString("What is the name of the product " +
                                    "that you want to remove from the stock?");
                            for (Product productToRemove :
                                    admin.getShopList().get(i).getStockFromRepository().getStock().keySet()) {
                                if (productToRemove.getName().equalsIgnoreCase(text)){
                                    product = productToRemove;
                                    admin.getShopList().get(i).getStockFromRepository().removeProducts(product);
                                }
                            }
                        }
                    }
                    break;
                case 4 :
                    text = readString("what is the name of the flower shop?");

                    for (int i = 0; i < admin.getShopList().size(); i++) {
                        if (admin.getShopList().get(i).getName().equalsIgnoreCase(text)){
                            admin.getShopList().get(i).showStock();
                        }
                    }
                    break;
                case 5 :
                    text = readString("what is the name of the flower shop?");

                    for (int i = 0; i < admin.getShopList().size(); i++) {
                        if (admin.getShopList().get(i).getName().equalsIgnoreCase(text)){
                            admin.getShopList().get(i).getStockFromRepository().printStock();
                        }
                    }
                    break;
                case 6 :
                    text = readString("what is the name of the flower shop?");

                    for (int i = 0; i < admin.getShopList().size(); i++) {
                        if (admin.getShopList().get(i).getName().equalsIgnoreCase(text)){
                            admin.getShopList().get(i).getStockFromRepository().getTotalStockValue();
                        }
                    }
                    break;
                case 7 :
                    break;
                case 8 :
                    break;
                case 9 :
                    break;
                case 0 :
                    System.out.println("Good Bye!");
                    working = false;
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (working);
    }
}
