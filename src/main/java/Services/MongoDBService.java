package Services;

import Database.MongoDBConnection;
import Model.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MongoDBService {
    private static final Logger logger = LoggerFactory.getLogger(MongoDBService.class);
    private static final String DATABASE_NAME = "dbFlowerShop";
    private static final String FLOWERS_SHOP_COLLECTION = "flowerShops";
    private MongoDatabase database;
    private MongoDBConnection connection;

    public MongoDBService() {
        connection = MongoDBConnection.getInstance();
        if (connection.createConnect()) {
            this.database = connection.getDatabase(DATABASE_NAME);
        } else {
            connection.logError("Failed to connect to MongoDB", null);
        }
    }

    public void insertFlowerShop(FlowerShop flowerShop) {
        logger.info("Inserting flower shop: {}", flowerShop.getName());
        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);

        Document doc = new Document("id", flowerShop.getShopId())
                .append("name", flowerShop.getName())
                .append("totalEarnings", flowerShop.calculateTotalEarnings())
                .append("products", flowerShop.getStockFromRepository().getStock().entrySet().stream()
                        .map(e -> new Document("productID", e.getKey().getProductID())
                                .append("name", e.getKey().getName())
                                .append("price", e.getKey().getPrice())
                                .append("type", getProductType(e.getKey()))
                                .append("quantity", e.getValue())
                                .append("specificAttributes", getProductSpecificAttributes(e.getKey())))
                        .collect(Collectors.toList()));

        collection.insertOne(doc);
        logger.info("Flower shop inserted: {}", flowerShop.getName());
    }

    public FlowerShop findFlowerShopById(int id) {
        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);
        Document query = new Document("id", id);
        Document doc = collection.find(query).first();
        if (doc != null) {
            String name = doc.getString("name");
            FlowerShop flowerShop = new FlowerShop(name);
            List<Document> products = (List<Document>) doc.get("products");
            if (products != null) {
                for (Document productDoc : products) {
                    Product product = documentToProduct(productDoc);
                    int quantity = productDoc.getInteger("quantity");
                    flowerShop.getStockFromRepository().addProduct(product, quantity);
                }
            }
            return flowerShop;
        }
        return null;
    }

    public List<FlowerShop> findAllFlowerShops() {
        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);
        List<FlowerShop> flowerShops = new ArrayList<>();
        for (Document doc : collection.find()) {
            String name = doc.getString("name");
            FlowerShop flowerShop = new FlowerShop(name);
            List<Document> products = (List<Document>) doc.get("products");
            if (products != null) {
                for (Document productDoc : products) {
                    Product product = documentToProduct(productDoc);
                    int quantity = productDoc.getInteger("quantity");
                    flowerShop.getStockFromRepository().addProduct(product, quantity);
                }
            }
            flowerShops.add(flowerShop);
        }
        return flowerShops;
    }

    public void updateFlowerShop(FlowerShop flowerShop) {
        logger.info("Updating flower shop: {}", flowerShop.getName());
        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);
        Document query = new Document("id", flowerShop.getShopId());
        Document update = new Document("$set", new Document("name", flowerShop.getName())
                .append("totalEarnings", flowerShop.calculateTotalEarnings())
                .append("products", flowerShop.getStockFromRepository().getStock().entrySet().stream()
                        .map(e -> new Document("productID", e.getKey().getProductID())
                                .append("name", e.getKey().getName())
                                .append("price", e.getKey().getPrice())
                                .append("type", getProductType(e.getKey()))
                                .append("quantity", e.getValue())
                                .append("specificAttributes", getProductSpecificAttributes(e.getKey())))
                        .collect(Collectors.toList())));
        collection.updateOne(query, update);
        logger.info("Flower shop updated: {}", flowerShop.getName());
    }

    public void deleteFlowerShop(int id) {
        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);
        Document query = new Document("id", id);
        collection.deleteOne(query);
    }

    private String getProductType(Product product) {
        if (product instanceof Tree) {
            return "Tree";
        } else if (product instanceof Flower) {
            return "Flower";
        } else if (product instanceof Decoration) {
            return "Decoration";
        }
        return "Unknown";
    }

    private Document getProductSpecificAttributes(Product product) {
        Document attributes = new Document();
        if (product instanceof Tree) {
            attributes.append("height", ((Tree) product).getHeight());
        } else if (product instanceof Flower) {
            attributes.append("color", ((Flower) product).getColor());
        } else if (product instanceof Decoration) {
            attributes.append("decorationType", ((Decoration) product).getType().toString());
        }
        return attributes;
    }

    private Product documentToProduct(Document doc) {
        String type = doc.getString("type");
        String name = doc.getString("name");
        double price = doc.getDouble("price");
        Document specificAttributes = doc.get("specificAttributes", Document.class);

        switch (type) {
            case "Tree":
                double height = specificAttributes.getDouble("height");
                return new Tree(name, price, height);
            case "Flower":
                String color = specificAttributes.getString("color");
                return new Flower(name, price, color);
            case "Decoration":
                Decoration.DecorationType decorationType = Decoration.DecorationType.valueOf(specificAttributes.getString("decorationType"));
                return new Decoration(name, price, decorationType);
            default:
                return new Product(name, price);
        }
    }




//    private FlowerShop documentToFlowerShop(Document doc) {
//        String name = doc.getString("name");
//        FlowerShop flowerShop = new FlowerShop(name);
//        List<Document> products = (List<Document>) doc.get("products");
//        if (products != null) {
//            for (Document productDoc : products) {
//                Product product = documentToProduct(productDoc);
//                int quantity = productDoc.getInteger("quantity");
//                flowerShop.getStockFromRepository().addProduct(product, quantity);
//            }
//        }
//        return flowerShop;
//    }
//
//
//    public void loadFlowerShopsFromMongoDB(FlowerShopManager admin) {
//        connection.logInfo("Loading flower shops from MongoDB");
//        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);
//        for (Document doc : collection.find()) {
//            FlowerShop flowerShop = documentToFlowerShop(doc);
//            admin.getShopList().add(flowerShop);
//        }
//    }

    public void insertTestDocument() {
        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);
        Document testDoc = new Document("testField", "testValue");
        Document testDoc1 = new Document("testField!!!!", "testValue!!!");
        collection.insertOne(testDoc);
        logger.info("Inserted test document");
    }



    public void close() {
        connection.closeConnection();
    }
}
