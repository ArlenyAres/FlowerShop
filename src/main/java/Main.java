import Database.MongoDBConnection;
import Model.Flower;
import Model.Product;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Instancia Unica de MongoDBConnection
        MongoDBConnection mongoDBConnection = MongoDBConnection.getInstance();

        try {
            mongoDBConnection.createConnect();
        } catch (MongoException e) {
            logger.error("MongoDB connection failed: " + e.getMessage());
            mongoDBConnection.closeConnection();
            return; // Salir del programa si la conexion falla
        }

        // PRUEBA DE INFORMACION DEL CLUSTER
        mongoDBConnection.showInfoCluster();

        // PRUEBA PARA MOSTRAR LAS BASES DE DATOS DISPONIBLES EN LA CONEXION
        mongoDBConnection.showDataBase();

        // Prueba para acceder a la colección
        MongoCollection<Product> flowers;
        try {
            flowers = mongoDBConnection.getPOJOCollection("dbFlowerShop", "flowers", Product.class);
        } catch (MongoException e) {
            logger.error("No se ha podido acceder a la colección: " + e.getMessage());
            mongoDBConnection.closeConnection();
            return; // Salir del programa si no se puede acceder a la colección
        }

        logger.info("Se ha accedido a la coleccion");

        // Ejemplo de como insertar un articulos en la coleccion Flower
        try {
            flowers.insertOne(new Flower("margarita", 3, "amarilla"));
            flowers.insertOne(new Flower("petunia", 8, "blanco"));
            logger.info("Articulos insertados correctamente en la coleccion Flower");
        } catch (MongoException e) {
            logger.error("Error al insertar articulos en la coleccion: " + e.getMessage());
        }

        // Cerrar la conexion bbdd
        mongoDBConnection.closeConnection();
    }
}
