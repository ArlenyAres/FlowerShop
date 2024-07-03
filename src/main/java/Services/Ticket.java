package Services;

import Model.Product;
import Model.Purchase;

import java.util.Map;

public class Ticket {
    private final Purchase purchase;

    public Ticket(Purchase purchase) {
        this.purchase = purchase;
    }

    public void createTicket() {
        System.out.println("-------------------------------------------------------------");
        System.out.println("                    " + purchase.getFlowerShopName() + "                       ");
        System.out.println("                      Ticket                     ");
        System.out.println("--------------------------------------------------------------");
        System.out.println("Date " + purchase.getDate() + "\n");

        for (Map.Entry<Product, Integer> entry : purchase.getPurchasedProductList().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("%-20s %-15s %-15s %-15s%n",
                    "Item", "Price", "Quant", "Subtotal");
            System.out.printf("%-20s %-15s %-15d %-15s%n",
                    product.getName(),
                    "€" + product.getPrice(),
                    quantity,
                    "€" + (quantity * product.getPrice()));
        }
        System.out.println("\nTOTAL: €" + purchase.calculateTotalPrice());
        System.out.println("\n--------------------------------------------------------------\n");
    }
}

