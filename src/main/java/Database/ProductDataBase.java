package Database;

import Model.Product;

import java.util.List;

public interface ProductDataBase {

    //Inserta un producto en la base de datos
    void insertProduct(Product product);

    //Busca un producto en la base de datos por su ID
    Product findById(int id);

    //Recupera todos los productos de la base de datos
    List<Product> findAllProducts();

    // Actualiza un producto existente en la base de datos.
    void updateProduct(Product product);

    // Elimina un producto de la base de datos por su ID.
    void deleteProduct(int id);
}
