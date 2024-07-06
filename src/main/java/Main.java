import Database.MongoDBConnection;
import Model.FlowerShop;
import Model.Product;
import Model.Purchase;
import Services.Ticket;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // TODO: Implement code

        MongoDBConnection mongoDBConnection = new MongoDBConnection();

        try {
            mongoDBConnection.createConnect();
        } catch (MongoException e){
            logger.error("MongoDB connection failed" + e.getMessage());
            mongoDBConnection.closeConection();
        }

        mongoDBConnection.showInfoCluster();

        mongoDBConnection.showDataBase();

    }
}
