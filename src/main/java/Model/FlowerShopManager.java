package Model;

import Services.Ticket;

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

    public void createFlorist(String name) {
        if (shopList.isEmpty()){
            FlowerShop shop = new FlowerShop(name);
            shopList.add(shop);
        } else {
            verifyShopList(name);
        }
    }
    public void verifyShopList(String name){
        for (int i = 0; i < shopList.size(); i++) {
            if (shopList.get(i).getName().equalsIgnoreCase(name)){
                System.out.println("This name is not available, please choose another name. ");
            } else {
                FlowerShop shop = new FlowerShop(name);
                shopList.add(shop);
            }
        }
    }

}
