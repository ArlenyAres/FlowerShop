package Database;


import Model.Product;

import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigFile;


import java.util.List;
import java.util.Properties;


public class MongoDBConnection extends Throwable implements ProductDataBase {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBConnection.class);
    private final ConnectionString connectionString;
    private MongoClient client = null;


    public MongoDBConnection() {
        Properties properties = null;

        try {
            ConfigFile configFile = new ConfigFile("mongodb.properties");
            properties = configFile.readPropertiesFile();
        } catch (Exception e){
            logger.error("Error load confic File {}", e.getMessage());
            this.connectionString = null;
            return;
        }

        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");


        String uri = String.format("mongodb://%s:%s@%s:%s", user, password, host, port);
        this.connectionString = new ConnectionString(uri);
    }


    public boolean createConnect() throws MongoException {

        if (this.connectionString == null) {
            logger.error( "Connection string is null, no exits" );
            return false ;
        }

        try {
            MongoClient mongoClient = MongoClients.create(this.connectionString);

            MongoDatabase database = mongoClient.getDatabase("admin");
            Bson comand = new BsonDocument("ping", new BsonInt64(1));
            Document result = database.runCommand(comand);

            logger.info("MongoDB connection enable" + result.toString() );

            this.client = mongoClient;

            return true;
        } catch (MongoException e) {
            logger.error("ERROR{}", e.getMessage());
            throw e;
        }

    }

    public void closeConection()  {
        if (this.client != null) {
            this.client.close();
            logger.info("The connetion is CLOSED");
        }
    }

    public void showInfoCluster(){
        if (this.client == null) {
            logger.warn("No connection established");
            return;
        }
        logger.info("\nShowing cluster info\n");
        logger.info(String.valueOf(this.client.getClusterDescription()));
    }

    public void showDataBase(){
        if (this.client == null) {
            logger.warn("No connection established");
            return;
        }
        logger.info("\nShowing databases");

        ListDatabasesIterable<Document> databases = this.client.listDatabases();
        int i = 1;
        for (Document document : databases) {
            System.out.println(String.format("%d-) %s", i, document.toString()));
            i++;
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
