package Database;


import Model.Product;

import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBConnection extends Throwable implements ProductDataBase {

    private final ConnectionString connectionString;
    private MongoClient client;


    public MongoDBConnection(String host, int port, String user, String password) {
        String uri = String.format("mongodb://%s:%s@%s:%d", user, password, host, port);
        this.connectionString = new ConnectionString(uri);
        this.client = null;
    }


    public boolean createConnect() throws MongoException {
        try {
            MongoClient mongoClient = MongoClients.create(this.connectionString);

            MongoDatabase database = mongoClient.getDatabase("admin");
            Bson comand = new BsonDocument("ping", new BsonInt64(1));
            Document result = database.runCommand(comand);

            System.out.println("MongoDB connection enable" + result.toString() );

            this.client = mongoClient;

            return true;
        } catch (MongoException e) {
            System.out.println( "ERROR" + e.getMessage() );
            throw e;
        }

    }

    public void closeConection()  {
        if (this.client != null) {
            this.client.close();
            System.out.println("The connetion is CLOSED");
        }
    }

//    public static synchronized MongoDBConnection getInstance() {
//        if( instance == null) {
//            instance = new MongoDBConnection();
//        }
//        return instance;
//    }


    @Override
    public void insertProduct(Product product) {
//        Document document = new Document("id", product.getId())
//                .append("name,", product.getName())
//                .append("price", product.getPrice());
//        collection.insertOne(document);
    }

    @Override
    public Product findById(int id) {
//        Document document = collection.find(eq("id", id)).first();
//        if (document != null) {
//            return new Product(document.getString("name"), document.getDouble("price"));
//        }
       return null;
    }

    @Override
    public List<Product> findAllProducts() {
        return List.of();
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(int id) {

    }
}
