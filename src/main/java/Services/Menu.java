package Services;

import Model.FlowerShop;
import Model.FlowerShopManager;
import Model.Product;
import Model.Purchase;

import static Services.Input.readInt;
import static Services.Input.readString;
import static Services.SubMenu.createProduct;
import static Services.SubMenu.createPurchase;

public class Menu {

    public static void menu() {
        FlowerShopManager admin = new FlowerShopManager();
        FlowerShop shop;
        Product product = null;
        Purchase purchase = null;
        Ticket ticket = null;
        int option = -1;
        int quantity = 0;
        String text = "";
        boolean working = true;

        do {
            System.out.println("Welcome, how may I help you?");
            System.out.println(" 1. Create flower shop\n " +
                    "2. Add product to the stock\n " +
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
                    text = readString("What is the name of the new flower shop?");
                    admin.createFlorist(text);
                    System.out.println("The new flower shop " + text + " was created!\n");
                    break;
                case 2 :
                    createProduct(admin);
                    System.out.println("The products were added to the stock");
                    break;
                case 3 :
                    text = readString("What is the name of the flower shop?");
                    shop = findShop(admin, text);
                    shop.getStockFromRepository().removeProducts();
                    break;
                case 4 :
                    text = readString("what is the name of the flower shop?");
                    shop = findShop(admin, text);
                    shop.showStock();
                    break;
                case 5 :
                    text = readString("what is the name of the flower shop?");
                    shop = findShop(admin, text);
                    shop.getStockFromRepository().printStock();
                    break;
                case 6 :
                    text = readString("what is the name of the flower shop?");
                    shop = findShop(admin, text);
                    System.out.println("The flower shop total value is: €" + shop.getStockValue());
                    break;
                case 7 :
                    text = readString("what is the name of the flower shop?");
                    shop = findShop(admin, text);
                    purchase = createPurchase(shop);
                    shop.addPurchaseToHistory(purchase);
                    ticket = new Ticket(purchase);
                    ticket.createTicket();
                    break;
                case 8 :
                    break;
                case 9 :
                    text = readString("what is the name of the flower shop?");
                    shop = findShop(admin, text);
                    System.out.println("The total earnings of the flower shop is: €" + shop.calculateTotalEarnings());
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

    public static FlowerShop findShop(FlowerShopManager admin, String shopName){
        FlowerShop shop = null;

        for (int i = 0; i < admin.getShopList().size(); i++) {
            if (admin.getShopList().get(i).getName().equalsIgnoreCase(shopName)){
                shop = admin.getShopList().get(i);
            }
        }
        return shop;
    }
}
