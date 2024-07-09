package Model;

import Services.Ticket;
import Services.MongoDBService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowerShopManager {

    private ArrayList<FlowerShop> shopList;

    public FlowerShopManager() {
        this.shopList = new ArrayList<>();
    }

    public ArrayList<FlowerShop> getShopList() {
        return shopList;
    }

    public void createFlorist(String name, MongoDBService mongoDBService) {
        if (shopList.isEmpty()){
            FlowerShop shop = new FlowerShop(name);
            shopList.add(shop);
            mongoDBService.insertFlowerShop(shop);
        } else {
            verifyShopList(name, mongoDBService);

        }
    }
//
//    public void createFlorist(String name, MongoDBService mongoDBService) {
//        FlowerShop shop = new FlowerShop(name);
//        shopList.add(shop);
//        mongoDBService.insertFlowerShop(shop);
//    }




    public void verifyShopList(String name, MongoDBService mongoDBService){
        for (int i = 0; i < shopList.size(); i++) {
            if (shopList.get(i).getName().equalsIgnoreCase(name)){
                System.out.println("This name is not available, please choose another name. ");
            } else {
                FlowerShop shop = new FlowerShop(name);
                shopList.add(shop);
                mongoDBService.insertFlowerShop(shop);
            }
        }
    }

    public void loadFlowerShopsFromDatabase(MongoDBService mongoDBService) {
        shopList = (ArrayList<FlowerShop>) mongoDBService.findAllFlowerShops();
    }

}
