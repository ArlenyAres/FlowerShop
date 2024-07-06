package Database;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigFile;


import java.util.Properties;


public class MongoDBConnection extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBConnection.class);
    private final ConnectionString connectionString;
    private MongoClient client = null;


    public MongoDBConnection() {
        Properties properties = null;

        try {
            ConfigFile configFile = new ConfigFile("mongodb.properties");
            properties = configFile.readPropertiesFile();
        } catch (Exception e) {
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

    private boolean getPing(MongoDatabase database){

        try {
            Bson comand = new BsonDocument("ping", new BsonInt64(1));
            Document result = database.runCommand(comand);

            logger.info("MongoDB connection enable, Result data ping: " + result.toString());

        } catch (MongoException e) {
            logger.error("ERROR Connection Ping {}", e.getMessage());
            return false;
        }
        return true;

    }


    public boolean createConnect() throws MongoException {

        if (this.connectionString == null) {
            logger.error("Connection string is null, no exits");
            return false;
        }

        try {
            MongoClient mongoClient = MongoClients.create(this.connectionString);

            MongoDatabase database = mongoClient.getDatabase("admin");
            if (getPing(database)) {
                this.client = mongoClient;
                return true;
            }

        } catch (MongoException e) {
            logger.error("ERROR{}", e.getMessage());
            throw e;
        }
            return false;

    }

    public void showInfoCluster() {
        if (this.client == null) {
            logger.warn("No connection established");
            return;
        }
        logger.info("\nShowing cluster info\n");
        logger.info(String.valueOf(this.client.getClusterDescription()));
    }

    public void showDataBase() {
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

    public MongoDatabase getDatabaseWhitCodec(String databaseName) throws MongoException {
        if (this.client == null) {
            if (!this.createConnect()) {
                throw new MongoException("Cannot connect to MongoDB server");
            }

        }
        CodecProvider pojoCoderProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCoderProvider));

        MongoDatabase database = this.client.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);

        if (!getPing(database)){
            throw new MongoException("Cannot connect to MongoDB server");
        }
        return database;
    }

    public MongoCollection<?> getCollection(String databaseName, String colletionName, Class entidad) throws MongoException {
        MongoDatabase database = getDatabaseWhitCodec(databaseName);
        return database.getCollection(colletionName, entidad);
    }


    public void closeConnection() {
        if (this.client != null) {
            this.client.close();
            logger.info("The connetion is CLOSED");
        }
    }




//    public static synchronized MongoDBConnection getInstance() {
//        if( instance == null) {
//            instance = new MongoDBConnection();
//        }
//        return instance;
//    }

}