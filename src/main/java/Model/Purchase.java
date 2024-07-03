package Model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Purchase {
    private String flowerShopName;
    private int purchaseID;
    private Date date;
    private Map<Product, Integer> purchasedProductsList;

    public Purchase(FlowerShop flowerShop, Map<Product, Integer> purchasedProductsList) {
        this.flowerShopName = flowerShop.getName();
        this.purchaseID = UUID.randomUUID().hashCode();
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

    public String getFlowerShopName() { return flowerShopName; }
}