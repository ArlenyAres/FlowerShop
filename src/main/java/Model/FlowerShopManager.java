package Model;

import Services.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowerShopManager {
    //private FlowerShop flowerShop;
    //private StockRepository stockRepository;
    //private List<Purchase> purchaseHistory;
    //private double totalEarnings;
    private ArrayList<FlowerShop> shopList;

    public FlowerShopManager() { //(String name)
        //this.flowerShop = new FlowerShop(name);
        //this.stockRepository = new StockRepository();
        //this.purchaseHistory = new ArrayList<>();
        //this.totalEarnings = 0.0;
        this.shopList = new ArrayList<>();
    }

    public ArrayList<FlowerShop> getShopList() {
        return shopList;
    }

    public void createFlorist(String name) {
        //this.flowerShop = new FlowerShop(name);
        FlowerShop shop = new FlowerShop(name);
        shopList.add(shop);
    }

/*    public void manageStock(Product product, int quantity) {
        stockRepository.addProduct(product, quantity);
    }

    public double getTotalValue() {
        return stockRepository.getTotalStockValue();
    }

    public void createPurchaseTicket(Map<Product, Integer> products) {
        Purchase purchase = new Purchase(flowerShop, products);
        Ticket ticket = new Ticket(purchase);
        ticket.createTicket();
        purchaseHistory.add(purchase);
        totalEarnings += purchase.calculateTotalPrice();
        stockRepository.removeProducts(products);
    }

    public double showTotalPurchaseValue() {
        return totalEarnings;
    }

    public void showStock() {
        stockRepository.printStock();
    }

    public void showProducts(Product product) {
        stockRepository.printProductStock(product);
    }

    public void showHistory() {
        for (Purchase purchase : purchaseHistory) {
            System.out.println(purchase);
        }
    }*/
}
