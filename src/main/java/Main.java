import Database.MongoDBConnection;
import Model.FlowerShop;
import Model.Product;
import Model.Purchase;
import Services.Ticket;
import com.mongodb.MongoException;


import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // TODO: Implement code

        MongoDBConnection mongoDBConnection = new MongoDBConnection("localhost", 27017, "admin", "root");

        try {
            mongoDBConnection.createConnect();
        } catch (MongoException e){
            System.out.println("MongoDB connection failed" + e.getMessage());
            mongoDBConnection.closeConection();
        }

    }
}
