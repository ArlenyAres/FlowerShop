package Services;

import Model.*;

import java.util.ArrayList;
import java.util.List;
import static Services.Input.*;

public class Menu {
    private MongoDBService mongoDBService;

    public Menu(MongoDBService mongoDBService) {
        this.mongoDBService = mongoDBService;
    }

    public void start() {
        FlowerShopManager admin = new FlowerShopManager();
        admin.loadFlowerShopsFromDatabase(mongoDBService);
        FlowerShop shop;
        Purchase purchase;
        int option = -1;
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
                    "10. Save flower shops to MongoDB\n " +
                    "0. Exit");
            option = readInt("Enter your option : ");

            switch (option) {
                case 1:
                    String text = readString("What is the name of the new flower shop?");
                    admin.createFlorist(text, mongoDBService);
                    System.out.println("The new flower shop " + text + " was created!\n");
                    break;
                case 2:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    SubMenu.createProduct(shop, mongoDBService);
                    mongoDBService.updateFlowerShop(shop); // Update the flower shop in MongoDB after adding a product
                    break;
                case 3:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    shop.getStockFromRepository().removeProducts(shop.getStockFromRepository().getStock());
                    mongoDBService.updateFlowerShop(shop); // Update the flower shop in MongoDB after removing a product
                    break;
                case 4:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    shop.showStock();
                    break;
                case 5:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    shop.getStockFromRepository().printStock();
                    break;
                case 6:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    System.out.println("The flower shop total value is: €" + shop.getStockValue());
                    break;
                case 7:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    purchase = SubMenu.createPurchase(shop, mongoDBService);
                    shop.addPurchaseToHistory(purchase);
                    mongoDBService.updateFlowerShop(shop);
                    mongoDBService.insertPurchase(shop, purchase);
                    break;
                case 8:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    showHistory(mongoDBService.getPurchases(shop));
                    break;
                case 9:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    System.out.println("The total earnings of the flower shop is: €" + shop.calculateTotalEarnings());
                    break;
                case 10:
                    saveFlowerShopsToMongoDB(admin);
                    break;
                case 0:
                    System.out.println("Good Bye!");
                    working = false;
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (working);
    }

    private void saveFlowerShopsToMongoDB(FlowerShopManager admin) {
        for (FlowerShop shop : admin.getShopList()) {
            mongoDBService.insertFlowerShop(shop);
        }
        System.out.println("Flower shops saved to MongoDB.");
    }

    public void showHistory(List<Purchase> purchases) {
        System.out.println("                       Purchases History                     ");
        for (Purchase purchase : purchases) {
            System.out.println("-------------------------------------------------------------");
            System.out.println(purchase.toString());
        }
        System.out.println("-------------------------------------------------------------");
    }

    private void flowerShopList(FlowerShopManager admin) {
        System.out.println("List of flower shops: ");
        for (int i = 0; i < admin.getShopList().size(); i++) {
            System.out.println((i + 1) + ". " + admin.getShopList().get(i).getName());
        }
    }

    private FlowerShop chooseFlowerShop(FlowerShopManager admin) {
        FlowerShop shop = null;
        int shopPosition = -1;
        boolean correct = false;

        while (!correct) {
            shopPosition = readInt("Choose a flower shop: ");
            if (shopPosition <= 0 || shopPosition > admin.getShopList().size()) {
                System.out.println("Invalid option, enter a valid shop.");
            } else {
                shop = admin.getShopList().get(shopPosition - 1);
                correct = true;
            }
        }
        return shop;
    }

}
