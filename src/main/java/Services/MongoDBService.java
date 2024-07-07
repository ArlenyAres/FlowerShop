package Services;

import Model.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDBService {
    private static final String DATABASE_NAME = "dbFlowerShop";
    private static final String FLOWERS_SHOP_COLLECTION = "flowerShops";
    private static final String PRODUCT_COLLECTION = "products";
    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoDBService() {
        this.mongoClient = MongoClients.create("mongodb://localhost:27017/flowerShop");
        this.database = mongoClient.getDatabase(DATABASE_NAME);
    }

    public void insertFlowerShop(FlowerShop flowerShop) {
        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);
        Document doc = new Document("name", flowerShop.getName())
                .append("stockValue", flowerShop.getStockValue())
                .append("totalEarnings",flowerShop.getTotalEarnings());
        collection.insertOne(doc);

    }

    public FlowerShop findFlowerShopById(int id) {
        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);
        Document query = new Document("id", id);
        Document doc = collection.find(query).first();
        if (doc != null) {
            String name = doc.getString("name");
            return new FlowerShop(name); //posiblem tengamos que configurar otros atributos
        }
        return null;
    }

    public List< FlowerShop> findAllFlowerShops() {
        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);
        List< FlowerShop > flowerShops = new ArrayList<>();
        for (Document doc : collection.find()) {
            String name = doc.getString("name");
            FlowerShop flowerShop = new FlowerShop(name);
            //posiblem tengamos que configurar otros atributos no se ...
            flowerShops.add(flowerShop);

        }
        return flowerShops;
    }

    public void updateFlowerShop(FlowerShop flowerShop) {
        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);
        Document query = new Document("id", flowerShop.getId());
        Document update = new Document("$set", new Document("name", flowerShop.getName()));
        collection.updateOne(query, update);
    }

    public void deleteFlowerShop(int id) {
        MongoCollection<Document> collection = database.getCollection(FLOWERS_SHOP_COLLECTION);
        Document query = new Document("id", id);
        collection.deleteOne(query);
    }

    public void insertProduct(Product product) {
        MongoCollection<Document> collection = database.getCollection(PRODUCT_COLLECTION);
        Document doc = new Document("name", product.getName())
                .append("price", product.getPrice());


        if (product instanceof Tree) {
            doc.append("type", "tree")
                    .append("height", ((Tree) product).getHeight());

        }else if (product instanceof Flower) {
            doc.append("type", "Flower")
                    .append("color", (((Flower) product).getColor()));

        }else if (product instanceof Decoration) {
            doc.append("type", "Decoration")
                    .append("decorationType",((Decoration) product).getType());
        }
        collection.insertOne(doc);
    }

    public Product findProductById(int id) {
        MongoCollection<Document> collection = database.getCollection(PRODUCT_COLLECTION);
        Document query = new Document("id", id);
        Document doc = collection.find(query).first();

        if (doc != null) {
            return documentToProduct(doc);
        }
        return null;
    }

    public Product findAllProducts() {
        MongoCollection<Document> collection = database.getCollection(PRODUCT_COLLECTION);
        List< Product > products = new ArrayList<>();
        for (Document doc : collection.find()) {
            products.add(documentToProduct(doc));
        }
        return products.get(0);
    }

//    public void deleteProduct(ObjectId id) {
//
//    }

        public void updateProduct(Product product) {
        MongoCollection<Document> collection = database.getCollection(PRODUCT_COLLECTION);
        Document query = new Document("id", product.getId());
        //Document query = new Document("_id", new ObjectId(product.getId()));
            Document update = new Document("$set", new Document("name", product.getName()))
                    .append("price", product.getPrice());

            if (product instanceof Tree) {
                update.append("$set", new Document("height", ((Tree) product).getHeight()));
            } else if (product instanceof Flower) {
                update.append("$set", new Document("color", ((Flower) product).getColor()));
            } else if (product instanceof Decoration) {
                update.append("$set", new Document("decorationType", ((Decoration) product).getType().toString()));
            }

            collection.updateOne(query, update);
        }




    private Product documentToProduct(Document doc) {
        String type = doc.getString("type");
        String name = doc.getString("name");
        double price = doc.getDouble("price");

        switch (type) {
            case "Tree":
                double height = doc.getDouble("height");
                return new Tree(name, price, height);
            case "Flower":
                String color = doc.getString("color");
                return new Flower(name, price, color);
            case "Decoration":
                Decoration.DecorationType decorationType = Decoration.DecorationType.valueOf(doc.getString("decorationType"));
                return new Decoration(name, price, decorationType);
            default:
                return new Product(name, price);
        }
    }

    public void close() {
        mongoClient.close();
    }


}

