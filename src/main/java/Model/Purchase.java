package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import Exception.InsufficientStockException;

public class Purchase {
    private FlowerShop flowerShop;
    private int purchaseID;
    private Date date;
    private Map<Product, Integer> purchaseProductsList;

    public Purchase(FlowerShop flowerShop, Map<Product, Integer> purchaseProductsList) {
        this.flowerShop = flowerShop;
        this.purchaseID = UUID.randomUUID().hashCode();
        this.date = new Date();
        this.purchaseProductsList = purchaseProductsList;
    }

//    public void addProductInPurchaseCart(Product product, int quantity) throws InsufficientStockException {
//        Map<Product, Integer> stock = flowerShop.getStock();
//
//        if (stock.containsKey(product)) {
//            int availableQuantity = stock.get(product);
//
//            if (availableQuantity >= quantity) {
//                purchaseProductsList.put(product, purchaseProductsList.getOrDefault(product, 0) + quantity);
//                stock.put(product, availableQuantity - quantity);
//                System.out.println(quantity + " " + product.getName() + "(s) added to the purchase cart.");
//            } else {
//                throw new InsufficientStockException("Insufficient quantity in stock for " + product.getName() + ".");
//            }
//        } else {
//            System.out.println(product.getName() + " is not available in stock.");
//        }
//    }

//    public void removeProductFromPurchaseCart(Product product, int quantity) {
//        if (purchaseProductsList.containsKey(product)) {
//            int currentQuantity = purchaseProductsList.get(product);
//
//            if (quantity <= currentQuantity) {
//                if (quantity == currentQuantity) {
//                    purchaseProductsList.remove(product);
//                } else {
//                    purchaseProductsList.put(product, currentQuantity - quantity);
//                }
//                Map<Product, Integer> stock = flowerShop.getStock();
//                stock.put(product, stock.getOrDefault(product, 0) + quantity);
//                System.out.println(quantity + " " + product.getName() + "(s) removed from the purchase cart.");
//            } else {
//                System.out.println("Cannot remove more than available quantity for " + product.getName() + ".");
//            }
//        } else {
//            System.out.println(product.getName() + " is not in the purchase cart.");
//        }
//    }

    public Map<Product, Integer> checkoutPurchase() {
        return purchaseProductsList;
    }

    public double calculateTotalPrice() {
        return purchaseProductsList.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public Map<Product, Integer> getPurchasedProductList() {
        return purchaseProductsList;
    }

    public Date getDate() {
        return date;
    }

    public String getFlowerShopName() { return flowerShop.getName(); }
}
