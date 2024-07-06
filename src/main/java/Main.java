import Database.MongoDBConnection;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // TODO: Implement code

        // PRUEBA DE CONEXION
        MongoDBConnection mongoDBConnection = new MongoDBConnection();

        try {
            mongoDBConnection.createConnect();
        } catch (MongoException e){
            logger.error("MongoDB connection failed" + e.getMessage());
            mongoDBConnection.closeConnection();
        }

        // PRUEBA DE INFORMACION DEL CLUSTER
        mongoDBConnection.showInfoCluster();

        // PRUEBA PARA MOSTRAR LAS BASE DE DATOS QUE HAY DISPONIBLES EN LA CONNECION
        mongoDBConnection.showDataBase();

    }
}
