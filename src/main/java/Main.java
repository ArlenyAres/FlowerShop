import Services.Menu;
import Services.MongoDBService;

public class Main {
    public static void main(String[] args) {
        MongoDBService mongoDBService = new MongoDBService();
        Menu menu = new Menu(mongoDBService);
        menu.start();


    }
}
