package Model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static Services.Input.readInt;
import static Services.Input.readString;

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

    public void removeProducts() { //(Map<Product, Integer> products)
        String productName = "";
        int counter = 0;
        int quantity = 0;

        productName = readString("Name of the product? ");
        quantity = readInt("How many products? \n");

        removeProcess(productName, counter, quantity);
    }

    public void removeProcess(String productName, int counter, int quantity){
        Iterator<Map.Entry<Product, Integer>> iterator;
        Map.Entry<Product, Integer> entry;
        Product productToRemove;

        iterator = stock.entrySet().iterator();

        while (iterator.hasNext() && quantity > 0) {
            entry = iterator.next();
            productToRemove = entry.getKey();
            if (productToRemove.getName().equalsIgnoreCase(productName)) {
                iterator.remove();
                quantity--;
                counter++;
            }
        }
        removeMessage(counter);
    }

    public void removeMessage(int counter) {
        if (counter == 0){
            System.out.println("Product not found\n");
        } else {
            System.out.println("Products removed from the stock : " + counter + "\n");
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
