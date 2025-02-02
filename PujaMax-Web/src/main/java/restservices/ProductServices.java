package restservices;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entities.Product;
import model.dao.ProductDAO;

@Path("/product")
public class ProductServices {
    private final ProductDAO productDAO;

    public ProductServices() {
        productDAO = new ProductDAO();
    }

    @Path("/list/{idLot}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> findProductsByLotId(@PathParam("idLot") int idLot) {
        return productDAO.findProductsByLotId(idLot);
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean createProduct(Product product) {
        return productDAO.create(product);
    }

    @Path("/find/{idProduct}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Product findProductById(@PathParam("idProduct") int idProduct) {
        return productDAO.findById(idProduct);
    }

    @Path("/update")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateProduct(Product product) {
        return productDAO.update(product);
    }

    @Path("/delete/{idProduct}")
    @DELETE
    public boolean removeProduct(@PathParam("idProduct") int idProduct) {
        return productDAO.remove(idProduct);
    }
}
