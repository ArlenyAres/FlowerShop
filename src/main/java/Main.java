import Services.Menu;
import Services.MongoDBService;

public class Main {
    public static void main(String[] args) {

        MongoDBService mongoDBService = new MongoDBService();  // Inicializamos la conexion a la
        Menu.menu();
    }
}
