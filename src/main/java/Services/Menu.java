package Services;

import Model.*;
import java.util.List;
import static Services.Input.*;

public class Menu {
    private MongoDBService mongoDBService;

    public Menu(MongoDBService mongoDBService) {
        this.mongoDBService = mongoDBService;
    }

    public void start() {
        FlowerShopManager admin = new FlowerShopManager();
        FlowerShop shop;
        Purchase purchase;
        Ticket ticket;
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
                    "11. Load flower shops from MongoDB\n " +
                    "12. Save products to MongoDB\n " +
                    "13. Load products from MongoDB\n " +
                    "0. Exit");
            option = readInt("Enter your option : ");

            switch (option) {
                case 1:
                    String text = readString("What is the name of the new flower shop?");
                    admin.createFlorist(text);
                    System.out.println("The new flower shop " + text + " was created!\n");
                    break;
                case 2:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    SubMenu.createProduct(shop, mongoDBService);
                    break;
                case 3:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    shop.getStockFromRepository().removeProducts(shop.getStockFromRepository().getStock());
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
                    ticket = new Ticket(purchase);
                    ticket.createTicket();
                    break;
                case 8:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    shop.showHistory();
                    break;
                case 9:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    System.out.println("The total earnings of the flower shop is: €" + shop.calculateTotalEarnings());
                    break;
                case 10:
                    saveFlowerShopsToMongoDB(admin);
                    break;
                case 11:
                    loadFlowerShopsFromMongoDB(admin);
                    break;
                case 12:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    saveProductsToMongoDB(shop);
                    break;
                case 13:
                    flowerShopList(admin);
                    shop = chooseFlowerShop(admin);
                    loadProductsFromMongoDB(shop);
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

    private void loadFlowerShopsFromMongoDB(FlowerShopManager admin) {
        List<FlowerShop> flowerShops = mongoDBService.findAllFlowerShops();
        admin.getShopList().addAll(flowerShops);
        System.out.println("Flower shops loaded from MongoDB.");
    }

    private void saveProductsToMongoDB(FlowerShop shop) {
        for (Product product : shop.getStockFromRepository().getStock().keySet()) {
            mongoDBService.insertProduct(product);
        }
        System.out.println("Products saved to MongoDB.");
    }

    private void loadProductsFromMongoDB(FlowerShop shop) {
        List<Product> products = mongoDBService.findAllProducts();
        for (Product product : products) {
            shop.getStockFromRepository().addProduct(product, 1); // Suponiendo una cantidad de 1 para cada producto
        }
        System.out.println("Products loaded from MongoDB.");
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
