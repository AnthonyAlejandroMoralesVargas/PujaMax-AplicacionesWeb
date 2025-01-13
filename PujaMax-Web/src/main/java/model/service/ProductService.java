package model.service;

import model.jpa.ProductJPA;
import model.entities.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private final ProductJPA productJPA;

    public ProductService() {
        productJPA = new ProductJPA();
    }

    public List<Product> findProductsByLotId(int idLot) throws SQLException {
        return productJPA.findProductsByLotId(idLot);
    }

    public boolean createProduct(Product product) {
        return productJPA.createProduct(product);
    }

    public Product findProductById(int idProduct) {
        return productJPA.findProductById(idProduct);
    }

    public boolean updateProduct(Product product) {
        return productJPA.updateProduct(product);
    }

    public boolean removeProduct(int idProduct) {
        return productJPA.removeProduct(idProduct);
    }
}
