package Model;

import Services.MongoDBService;
import java.util.ArrayList;

public class FlowerShopManager {

    private ArrayList<FlowerShop> shopList;

    public FlowerShopManager() {
        this.shopList = new ArrayList<>();
    }

    public ArrayList<FlowerShop> getShopList() {
        return shopList;
    }

    public void createFlorist(String name, MongoDBService mongoDBService) {
        FlowerShop shop = new FlowerShop(name);

        if (shopList.isEmpty()){
            shopList.add(shop);
            mongoDBService.insertFlowerShop(shop);
        } else {
            verifyShopList(name, mongoDBService);
        }
    }

    public void verifyShopList(String name, MongoDBService mongoDBService){
        FlowerShop shop = new FlowerShop(name);

        for (int i = 0; i < shopList.size(); i++) {
            if (shopList.get(i).getName().equalsIgnoreCase(name)){
                System.out.println("This name is not available, please choose another name. ");
            } else if (!shopList.get(i).getName().equalsIgnoreCase(name) && i == (shopList.size()-1)){
                shopList.add(shop);
                mongoDBService.insertFlowerShop(shop);
            }
        }
    }

    public void loadFlowerShopsFromDatabase(MongoDBService mongoDBService) {
        shopList = (ArrayList<FlowerShop>) mongoDBService.findAllFlowerShops();
    }

}
