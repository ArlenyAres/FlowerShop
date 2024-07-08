package Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import Exception.InsufficientStockException;

public class Purchase {
    private FlowerShop flowerShop;
    private String purchaseID;
    private Date date;
    private Map<Product, Integer> stock;
    private Map<Product, Integer> purchaseCart;

    public Purchase(FlowerShop flowerShop, Map<Product, Integer> stock) {
        this.flowerShop = flowerShop;
        this.purchaseID = UUID.randomUUID().toString();
        this.date = new Date();
        this.stock = stock;
        this.purchaseCart = new HashMap<>();
    }

    public void addProductInPurchaseCart(Product product, int quantity) throws InsufficientStockException {
        if (stock.containsKey(product)) {
            int availableQuantity = stock.get(product);
            int quantityInCart = purchaseCart.getOrDefault(product, 0);
            int totalQuantity = quantity + quantityInCart;

            if (availableQuantity >= totalQuantity) {
                purchaseCart.put(product, totalQuantity);
                stock.put(product, availableQuantity - quantity);
                System.out.println(quantity + " " + product.getName() + "(s) added to the purchase cart.");
            } else {
                throw new InsufficientStockException("Insufficient quantity in stock for " + product.getName() + ".");
            }
        } else {
            System.out.println(product.getName() + " is not available in stock.");
        }
    }

    public void removeProductFromPurchaseCart(Product product, int quantity) {
        if (stock.containsKey(product)) {
            int currentQuantity = stock.get(product);

            if (quantity <= currentQuantity) {
                if (quantity == currentQuantity) {
                    purchaseCart.remove(product);
                } else {
                    stock.put(product, currentQuantity - quantity);
                }
                stock.put(product, stock.getOrDefault(product, 0) + quantity);
                System.out.println(quantity + " " + product.getName() + "(s) removed from the purchase cart.");
            } else {
                System.out.println("Cannot remove more than available quantity for " + product.getName() + ".");
            }
        } else {
            System.out.println(product.getName() + " is not in the purchase cart.");
        }
    }

    public Map<Product, Integer> checkoutPurchase() {
        return stock;
    }

    public double calculateTotalPrice() {
        return purchaseCart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public Map<Product, Integer> getPurchasedProductList() {
        return purchaseCart;
    }

    public Date getDate() {
        return date;
    }

    public String getFlowerShopName() { return flowerShop.getName(); }

    public String getPurchaseID() { return purchaseID; }

    @Override
    public String toString() {
        StringBuilder productListString = new StringBuilder();
        double totalValue = 0;

        // Header for product details
        productListString.append(String.format("%-20s %-10s %-10s %-10s\n", "Product", "Quantity", "Unit Price", "Price"));

        for (Map.Entry<Product, Integer> entry : purchaseCart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double unitPrice = product.getPrice();
            double price = unitPrice * quantity;
            totalValue += price;

            // Append product details
            productListString.append(String.format("%-20s %-10d %-10.2f %-10.2f\n", product.getName(), quantity, unitPrice, price));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return "Purchase ID: " + purchaseID + ":" +
                "\n  FlowerShop: " + flowerShop.getName() +
                ", \n  Date: " + sdf.format(date) +
                ", \n  Purchased Products List:\n" + productListString.toString() +
                "\n";
    }
}
