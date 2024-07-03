package Model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Purchase {
    private int purchaseID;
    private Date date;
    private Map<Product, Integer> purchasedProductsList;

    public Purchase(Map<Product, Integer> purchasedProductsList) {
        this.purchaseID = Integer.valueOf(UUID.randomUUID().toString());
        this.date = new Date();
        this.purchasedProductsList = purchasedProductsList;
    }

    public double calculateTotalPrice() {
        double totalPrice = purchasedProductsList.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
        return totalPrice;
    }

    public Map<Product, Integer> getPurchasedProductList() {
        return purchasedProductsList;
    }

    public Date getDate() {
        return date;
    }
}