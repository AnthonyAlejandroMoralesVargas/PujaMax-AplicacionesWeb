package model.service;

import model.dao.ProductDAO;
import model.entities.Product;
import java.util.List;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService() {
        productDAO = new ProductDAO();
    }

    public List<Product> findProductsByLotId(int idLot) {
        return productDAO.findProductsByLotId(idLot);
    }

    public boolean createProduct(Product product) {
        return productDAO.create(product);
    }

    public Product findProductById(int idProduct) {
        return productDAO.findById(idProduct);
    }

    public boolean updateProduct(Product product) {
        return productDAO.update(product);
    }

    public boolean removeProduct(int idProduct) {
        return productDAO.removeProduct(idProduct);
    }
}
