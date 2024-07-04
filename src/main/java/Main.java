import Model.FlowerShop;
import Model.Product;
import Model.Purchase;
import Services.Ticket;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // TODO: Implement code

        Product product1 = new Product("product1", 20.0);
        Product product2 = new Product("product2", 10.0);

        Map<Product, Integer> purchasedProducts = new HashMap<>();
        purchasedProducts.put(product1, 2); // 2 units of product1
        purchasedProducts.put(product2, 3); // 3 units of product2

// Create a new purchase
        FlowerShop flowerShop = new FlowerShop("Floristeria");
        flowerShop.getStock().add(product1);
        flowerShop.getStock().add(product2);

        Purchase purchase1 = new Purchase(flowerShop, purchasedProducts);
        Ticket ticket = new Ticket(purchase1);
        ticket.createTicket();

    }
}
