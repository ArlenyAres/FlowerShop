package Main;

import Model.*;
import Model.Decoration.DecorationType;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de FlowerShopManager
        FlowerShopManager flowerShopManager = new FlowerShopManager("Mi Floristería");

        // Crear algunos productos
        Product tree1 = new Tree("Roble", 50.0, 2.5);
        Product tree2 = new Tree("Pino", 30.0, 1.8);
        Product flower1 = new Flower("Rosa", 10.0, "Rojo");
        Product flower2 = new Flower("Lirio", 12.0, "Blanco");
        Product decoration1 = new Decoration("Maceta de Madera", 20.0, DecorationType.WOOD);
        Product decoration2 = new Decoration("Maceta de Plástico", 15.0, DecorationType.PLASTIC);

        // Añadir productos al stock
        flowerShopManager.manageStock(tree1, 10);
        flowerShopManager.manageStock(tree2, 5);
        flowerShopManager.manageStock(flower1, 20);
        flowerShopManager.manageStock(flower2, 15);
        flowerShopManager.manageStock(decoration1, 8);
        flowerShopManager.manageStock(decoration2, 10);

        // Mostrar el stock inicial
        System.out.println("Stock inicial:");
        flowerShopManager.showStock();

        // Crear una lista de productos para un ticket de compra
        Map<Product, Integer> purchase1 = new HashMap<>();
        purchase1.put(tree1, 2);
        purchase1.put(flower1, 5);
        purchase1.put(decoration1, 1);

        // Crear un ticket de compra
        flowerShopManager.createPurchaseTicket(purchase1);

        // Mostrar el stock después de la compra
        System.out.println("\nStock después de la primera compra:");
        flowerShopManager.showStock();

        // Mostrar el valor total del stock
        System.out.println("\nValor total del stock: " + flowerShopManager.getTotalValue());

        // Mostrar el historial de compras
        System.out.println("\nHistorial de compras:");
        flowerShopManager.showHistory();

        // Mostrar el valor total de las compras
        System.out.println("\nValor total de las compras: " + flowerShopManager.showTotalPurchaseValue());
    }
}
