package Model;

import Services.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowerShopManager {

    private ArrayList<FlowerShop> shopList;

    public FlowerShopManager() { //(String name)

        this.shopList = new ArrayList<>();
    }

    public ArrayList<FlowerShop> getShopList() {
        return shopList;
    }

    public void createFlorist(String name) {

        FlowerShop shop = new FlowerShop(name);
        shopList.add(shop);
    }

}
