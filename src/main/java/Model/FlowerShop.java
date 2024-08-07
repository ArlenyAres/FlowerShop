package Model;

import java.util.ArrayList;
import java.util.Map;

public class FlowerShop {

    private int id;
    private static int nextId = 1;
    private String name;
    private StockRepository stockFromRepository;
    private ArrayList<Purchase> purchaseHistory;
    private double totalEarnings;

    public FlowerShop(String name) {
        this.id = nextId++;
        this.name = name;
        this.stockFromRepository = new StockRepository();
        this.purchaseHistory = new ArrayList<>();
        this.totalEarnings = 0.0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StockRepository getStockFromRepository() {
        return stockFromRepository;
    }

    public double getStockValue() {
        return stockFromRepository.getTotalStockValue();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void showStock() {
        int decorationStock = 0;
        int flowerStock = 0;
        int treeStock = 0;

        if (stockFromRepository.getStock().isEmpty()) {
            System.out.println("The stock is empty");
        } else {
            verifyStock(decorationStock, flowerStock, treeStock);
        }
    }

    public void verifyStock(int decorationStock, int flowerStock, int treeStock){
        for (Map.Entry<Product, Integer> entry : stockFromRepository.getStock().entrySet()){
            if (entry.getKey() instanceof Decoration){
                decorationStock += entry.getValue();
            } else if (entry.getKey() instanceof Flower) {
                flowerStock += entry.getValue();
            } else if (entry.getKey() instanceof Tree) {
                treeStock += entry.getValue();
            }
        }
        System.out.println("Decorations : " + decorationStock +
                "\nFlowers : " + flowerStock +
                "\nTrees : " + treeStock + "\n");
    }

    public void addPurchaseToHistory(Purchase purchase) {
        purchaseHistory.add(purchase);
    }

    public double calculateTotalEarnings() {
        double total = 0.0;
        for (Purchase purchase : purchaseHistory) {
            total += purchase.calculateTotalPrice();
        }
        return total;
    }

}
