package Model;

import java.util.ArrayList;

public class FlowerShop {

    // Attributes
    private int id;
    private static int nextId = 1;
    private String name;
    private ArrayList<Product> stock;
    private double stockValue;

    // Constructor
    public FlowerShop (int id, String name) {
        this.id = nextId++;
        this.name = name;
        this.stockValue = totalValue();
        this.stock = new ArrayList<>();
    }

    // Getters
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public double getStockValue() {
        return stockValue;
    }

    // Setters
    public void setName(String name){
        this.name = name;
    }

    // General methods
    public void showStock(){
        int decorationStock = 0;
        int flowerStock = 0;
        int treeStock = 0;

        if (this.stock.isEmpty()){
            System.out.println("The stock is empty");
        } else {
            verifyStock(decorationStock,flowerStock,treeStock);
            System.out.println("Decorations : " + decorationStock +
                    "\nFlowers : " + flowerStock +
                    "\nTrees : " + treeStock);
        }
    }

    public void verifyStock(int decorationStock, int flowerStock, int treeStock){
        for (Product product : this.stock){
            if (product.getClass() == Decoration.class){
                decorationStock++;
            } else if (product.getClass() == Flower.class) {
                flowerStock++;
            } else if (product.getClass() == Tree.class) {
                treeStock++;
            }
        }
    }

    public double totalValue(){
        double totalValue = 0d;

        if (!this.stock.isEmpty()){
            for (int i = 0; i < stock.size(); i++) {
                totalValue += stock.get(i).getPrice();
            }
        }
        return totalValue;
    }
}
