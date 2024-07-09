package Model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import Exception.InsufficientStockException;
import org.bson.Document;

public class Purchase {
    private FlowerShop flowerShop;
    private String purchaseID;
    private Date date;
    private Map<Product, Integer> stock;
    private Map<Product, Integer> purchaseCart;

    public Purchase(FlowerShop shop, Document purchaseDoc) {
        this.flowerShop = shop;
        this.purchaseID = purchaseDoc.getString("id");
        this.date = purchaseDoc.getDate("date");
        this.stock = shop.getStockFromRepository().getStock();
        this.purchaseCart = new HashMap<>();

        List<Document> productDocs = purchaseDoc.getList("products", Document.class);
        productDocs.forEach(productDoc -> {
            Product product = new Product(productDoc.getString("name"), productDoc.getDouble("price"));
            purchaseCart.put(product, productDoc.getInteger("quantity"));
        });
    }

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
        if (purchaseCart.containsKey(product)) {
            int quantityInCart = purchaseCart.get(product);

            if (quantity <= quantityInCart) {
                if (quantity == quantityInCart) {
                    purchaseCart.remove(product);
                } else {
                    purchaseCart.put(product, quantityInCart - quantity);
                }
                stock.put(product, stock.getOrDefault(product, 0) + quantity);
                System.out.println(quantity + " " + product.getName() + "(s) removed from the purchase cart.");
            } else {
                System.out.println("Cannot remove more than available quantity in the cart for " + product.getName() + ".");
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

        productListString.append(String.format("%-20s %-10s %-10s %-10s\n", "Product", "Quantity", "Unit Price", "Price"));

        for (Map.Entry<Product, Integer> entry : purchaseCart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double unitPrice = product.getPrice();
            double price = unitPrice * quantity;
            totalValue += price;

            productListString.append(String.format("%-20s %-10d %-10.2f %-10.2f\n", product.getName(), quantity, unitPrice, price));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return "Purchase ID: " + purchaseID + ":" +
                "\n  FlowerShop: " + flowerShop.getName() +
                ", \n  Date: " + sdf.format(date) +
                ", \n  Purchased Products List:\n" + productListString.toString();
    }
}
