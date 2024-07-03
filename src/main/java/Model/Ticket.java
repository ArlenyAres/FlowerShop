package Model;

import java.util.Map;

public class Ticket {
    private final Purchase purchase;

    public Ticket(Purchase purchase) {
        this.purchase = purchase;
    }

    public void createTicket() {
        System.out.println("-----------TICKET----------");
        System.out.println("Date: " + purchase.getDate());

        for (Map.Entry<Product, Integer> entry : purchase.getPurchasedProductList().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("Product: " + product + "€" + product.getPrice() + " | " + "Quant: " + quantity + " | " + "total: €" + (quantity * product.getPrice()));
            System.out.println("TOTAL: €" + purchase.calculateTotalPrice());
        }
    }
}

