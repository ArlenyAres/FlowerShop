package Model;

import java.util.HashMap;
import java.util.Map;

public class FlowerShop {

    // Attributes
    private int id;
    private static int nextId = 1;
    private String name;
    private Map<Product, Integer> stock;
    private double stockValue;

    // Constructor
    public FlowerShop(String name) {
        this.id = nextId++;
        this.name = name;
        this.stock = new HashMap<>();
        this.stockValue = totalValue();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getStockValue() {
        return stockValue;
    }

    public Map<Product, Integer> getStock() {
        return stock;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    // General methods
    public void showStock() {
        int decorationStock = 0;
        int flowerStock = 0;
        int treeStock = 0;

        if (stock.isEmpty()) {
            System.out.println("The stock is empty");
        } else {
            verifyStock(decorationStock, flowerStock, treeStock);
            System.out.println("Decorations : " + decorationStock +
                    "\nFlowers : " + flowerStock +
                    "\nTrees : " + treeStock);
        }
    }

    public void verifyStock(int decorationStock, int flowerStock, int treeStock) {
        for (Map.Entry<Product, Integer> entry : stock.entrySet()) {
            Product product = entry.getKey();
            if (product.getClass() == Decoration.class) {
                decorationStock += entry.getValue();
            } else if (product.getClass() == Flower.class) {
                flowerStock += entry.getValue();
            } else if (product.getClass() == Tree.class) {
                treeStock += entry.getValue();
            }
        }
    }

    public double totalValue() {
        double totalValue = 0d;

        if (!stock.isEmpty()) {
            for (Map.Entry<Product, Integer> entry : stock.entrySet()) {
                totalValue += entry.getKey().getPrice() * entry.getValue();
            }
        }
        return totalValue;
    }
}
