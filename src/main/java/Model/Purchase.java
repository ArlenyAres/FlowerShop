package Model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

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

    public double calculateTotalPrice() {
        double totalPrice = purchaseProductsList.entrySet().stream()
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
