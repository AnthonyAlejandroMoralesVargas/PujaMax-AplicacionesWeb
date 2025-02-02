package restservices;

import java.sql.SQLException;
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
import model.jpa.ProductJPA;

@Path("/product")
public class ProductServices {
	
	private final ProductJPA productJPA;

    public ProductServices() {
        productJPA = new ProductJPA();
    }

    @Path("/list/{idLot}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public List<Product> findProductsByLotId(@PathParam("idLot")int idLot) throws SQLException {
        return productJPA.findProductsByLotId(idLot);
    }

    @Path("/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    public boolean createProduct(Product product) {
        return productJPA.createProduct(product);
    }
    
    @Path("/find/{idProduct}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public Product findProductById(@PathParam("idProduct")int idProduct) {
        return productJPA.findProductById(idProduct);
    }

    @Path("/update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
    public boolean updateProduct(Product product) {
        return productJPA.updateProduct(product);
    }
    
    @Path("/delete/{idProduct}")
	@DELETE
    public boolean removeProduct(@PathParam("idProduct")int idProduct) {
        return productJPA.removeProduct(idProduct);
    }
}
