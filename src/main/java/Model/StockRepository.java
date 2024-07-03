package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockRepository {
    private Map<Product, Integer> stock;

    public StockRepository() {
        this.stock = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        stock.put(product, stock.getOrDefault(product, 0) + quantity);
    }

    public void removeProducts(List<Product> products) {
        for (Product product : products) {
            int currentStock = stock.getOrDefault(product, 0);
            if (currentStock > 0) {
                stock.put(product, currentStock - 1);
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
}
