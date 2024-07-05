package Database;


import Model.Product;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBConnection implements ProductDataBase {

    private final String ConnectionString connectionString;
    private MongoClient client;
    private MongoDatabase database;

    public MongoDBConnection() {
        //in progress
    }

    public static synchronized MongoDBConnection getInstance() {
        if( instance == null) {
            instance = new MongoDBConnection();
        }
        return instance;
    }


    @Override
    public void insertProduct(Product product) {
        Document document = new Document("id", product.getId())
                .append("name,", product.getName())
                .append("price", product.getPrice());
        collection.insertOne(document);
    }

    @Override
    public Product findById(int id) {
        rDocument document = collection.find(eq("id", id)).first();
        if (document != null) {
            return new Product(document.getString("name"), document.getDouble("price"));
        }
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
