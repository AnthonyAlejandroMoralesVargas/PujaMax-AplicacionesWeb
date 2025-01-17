package controllers;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entities.Address;
import model.entities.Auctioneer;
import model.entities.Bidder;
import model.entities.Product;
import model.service.AddressService;
import model.service.BidService;
import model.service.ProductService;

@WebServlet("/PlaceBidController")
public class PlaceBidController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.router(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.router(req, resp);
    }

    private void router(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Control logic
        String route = (req.getParameter("route") == null) ? "list" : req.getParameter("route");

        switch (route) {
            case "listProduct":
			try {
				this.listProduct(req, resp);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            case "bid":
                this.newHighBid(req, resp);
                break;
            default:
                throw new IllegalArgumentException("Unknown route: " + route);
        }
    }


    private void listProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		// Verificar si se solicita una imagen específica
		String idProductParam = req.getParameter("idProduct");
		if (idProductParam != null) {
			try {
				int idProduct = Integer.parseInt(idProductParam);
				ProductService productService = new ProductService();
				Product product = productService.findProductById(idProduct);

				if (product == null || product.getPhoto() == null) {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
					return;
				}

				// Configurar el tipo de contenido y enviar la imagen
				resp.setContentType("image/jpeg");
				resp.setContentLength(product.getPhoto().length);
				resp.getOutputStream().write(product.getPhoto());
			} catch (NumberFormatException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Product ID");
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error retrieving image: " + e.getMessage());
			}
			return; // Salir después de procesar la imagen
		}

		// Lógica para listar productos
		try {
			int idLot = Integer.parseInt(req.getParameter("idLot"));
			ProductService productService = new ProductService();
			List<Product> products = productService.findProductsByLotId(idLot);

			req.setAttribute("products", products);
			req.setAttribute("idLot", idLot);
			req.getRequestDispatcher("jsp/BIDDERR_LOT.jsp").forward(req, resp);
		} catch (NumberFormatException e) {
			req.setAttribute("messageType", "error");
			req.setAttribute("message", "Invalid Lot ID provided.");
			req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
		}
	}
    
    private void newHighBid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("idProduct"));
        int bidderId = ((Bidder) req.getSession().getAttribute("user")).getId();
        double bidAmount = Double.parseDouble(req.getParameter("price"));
        BidService bidService = new BidService();
        try {
            bidService.updateBid(idBid);
            req.setAttribute("message", "Bid placed successfully.");
            req.getRequestDispatcher("LotManagementController?route=showProduct&productId=" + productId).forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error placing bid", e);
        }
    }



}
