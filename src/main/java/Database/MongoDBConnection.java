package Database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import com.mongodb.client.ListDatabasesIterable;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigFile;

import java.util.Properties;

public class MongoDBConnection {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBConnection.class);
    private static MongoDBConnection instance;
    private final ConnectionString connectionString;
    private MongoClient client = null;

    // Constructor privado para evitar las instancias adicionales
    private MongoDBConnection() {
        Properties properties = null;

        try {
            ConfigFile configFile = new ConfigFile("mongodb.properties");
            properties = configFile.readPropertiesFile();
        } catch (Exception e) {
            logger.error("Error load config file {}", e.getMessage());
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

    //  para obtener la instancia de conexion de la clase
    public static synchronized MongoDBConnection getInstance() {
        if (instance == null) {
            instance = new MongoDBConnection();
        }
        return instance;
    }

    public boolean createConnect() throws MongoException {
        if (this.connectionString == null) {
            logger.error("Connection string is null, no exists");
            return false;
        }

        try {
            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(pojoCodecProvider)
            );

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(this.connectionString)
                    .codecRegistry(pojoCodecRegistry)
                    .build();

            this.client = MongoClients.create(settings);
            MongoDatabase database = this.client.getDatabase("admin");

            return getPing(database);

        } catch (MongoException e) {
            logger.error("ERROR {}", e.getMessage());
            throw e;
        }
    }

    private boolean getPing(MongoDatabase database) {
        try {
            Document result = database.runCommand(new Document("ping", 1));
            logger.info("MongoDB connection enable, Result data ping: " + result.toString());
        } catch (MongoException e) {
            logger.error("ERROR Connection Ping {}", e.getMessage());
            return false;
        }
        return true;
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
            logger.info(String.format("%d-) %s", i, document.toString()));
            i++;
        }
    }

    public <T> MongoCollection<T> getPOJOCollection(String databaseName, String collectionName, Class<T> clazz) throws MongoException {
        MongoDatabase database = getDatabase(databaseName);
        return database.getCollection(collectionName, clazz);
    }

    public MongoDatabase getDatabase(String databaseName) throws MongoException {
        if (this.client == null) {
            if (!this.createConnect()) {
                throw new MongoException("Cannot connect to MongoDB server");
            }
        }
        return this.client.getDatabase(databaseName);
    }

    public void closeConnection() {
        if (this.client != null) {
            this.client.close();
            logger.info("The connection is CLOSED");
        }
    }
}
