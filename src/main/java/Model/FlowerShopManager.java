package Model;

import java.util.ArrayList;
import java.util.List;


public class FlowerShopManager {
    private String name;
    private StockRepository stockRepository;
//    private List<Ticket> purchaseHistory;
    private double totalEarnings;

    public FlowerShopManager(String name) {
        this.name = name;
        this.stockRepository = new StockRepository();
//        this.purchaseHistory = new ArrayList<>();
        this.totalEarnings = 0.0;
    }

    public void createFlorist(String name) {
        this.name = name;
    }

    public void manageStock(Product product, int quantity) {
        stockRepository.addProduct(product, quantity);
    }

    public double getTotalValue() {
        return stockRepository.getTotalStockValue();
    }

//    public void createPurchaseTicket(List<Product> products) {
//        Ticket ticket = new Ticket(products);
//        purchaseHistory.add(ticket);
//        totalEarnings += ticket.getTotalPrice();
//        stockRepository.removeProducts(products);
//    }

    public double showTotalPurchaseValue() {
        return totalEarnings;
    }

    public void showStock() {
        stockRepository.printStock();
    }

//    public void showHistory() {
//        for (Ticket ticket : purchaseHistory) {
//            System.out.println(ticket);
//        }
//    }
}
