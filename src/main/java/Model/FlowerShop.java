package Model;

public class FlowerShop {

    private int id;
    private static int nextId = 1;
    private String name;
    private StockRepository stock;
    private double stockValue;

    public FlowerShop (String name) {
        this.id = nextId++;
        this.name = name;
        this.stock = new StockRepository();
        this.stockValue = stock.getTotalStockValue();
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void showStock(){
        int decorationStock = 0;
        int flowerStock = 0;
        int treeStock = 0;

        if (stock.getStock().isEmpty()){
            System.out.println("The stock is empty");
        } else {
            verifyStock(decorationStock,flowerStock,treeStock);
            System.out.println("Decorations : " + decorationStock +
                    "\nFlowers : " + flowerStock +
                    "\nTrees : " + treeStock);
        }
    }

    public void verifyStock(int decorationStock, int flowerStock, int treeStock){
        for (Product product : stock.getStock().keySet()){
            if (product.getClass() == Decoration.class){
                decorationStock++;
            } else if (product.getClass() == Flower.class) {
                flowerStock++;
            } else if (product.getClass() == Tree.class) {
                treeStock++;
            }
        }
    }

}
