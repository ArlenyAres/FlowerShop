import Database.MongoDBConnection;
import Services.Menu;
import Services.MongoDBService;
import com.mongodb.MongoException;

public class Main {
    public static void main(String[] args) {
        MongoDBService mongoDBService = new MongoDBService();

        MongoDBConnection connection = MongoDBConnection.getInstance();
        try {
            if (connection.createConnect()) {
                connection.showInfoCluster();
                connection.showDataBase();

            }
        } catch (MongoException e) {
            connection.logError("MongoDB connection failed", e);
            connection.closeConnection();
            return;
        }

        Menu menu = new Menu(mongoDBService);
        menu.start();
    }
}
