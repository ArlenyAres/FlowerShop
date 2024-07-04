package Model;

import java.util.HashMap;
import java.util.Map;

public class StockRepository {
    private Map<Product, Integer> stock;

    public StockRepository() {
        this.stock = new HashMap<>();
    }

    public Map<Product, Integer> getStock() {
        return stock;
    }

    public void addProduct(Product product, int quantity) {
        stock.put(product, stock.getOrDefault(product, 0) + quantity);
    }

    public void removeProducts(Map<Product, Integer> products) {
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            int currentStock = stock.getOrDefault(product, 0);
            if (currentStock >= quantity) {
                stock.put(product, currentStock - quantity);
            }
        }
    }

    public double getTotalStockValue() {
        double totalValue = 0.0;
        for (Map.Entry<Product, Integer> entry : stock.entrySet()) {
            totalValue += entry.getKey().getPrice() * entry.getValue();
        }
        return totalValue;
    }

    public void printStock() {
        for (Map.Entry<Product, Integer> entry : stock.entrySet()) {
            System.out.println("Product: " + entry.getKey().getName() + ", Quantity: " + entry.getValue());
        }
    }

    public void printProductStock(Product product) {
        int quantity = stock.getOrDefault(product, 0);
        System.out.println("Product: " + product.getName() + ", Quantity: " + quantity);
    }
}
