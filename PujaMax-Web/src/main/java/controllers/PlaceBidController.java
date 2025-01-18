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

import model.entities.Bid;
import model.entities.Bidder;
import model.entities.Product;
import model.jpa.BidJPA;
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
        String route = (req.getParameter("route") == null) ? "list" : req.getParameter("route");
        System.out.println("Route received: " + route); // Debug
        System.out.println("ID Product: " + req.getParameter("idProduct")); // Debug

        try {
            switch (route) {
                case "list":
                    listProduct(req, resp);
                    break;
                case "productDetails":
                    showProductDetails(req, resp);
                    break;
                case "bids":
                    showBidsForProduct(req, resp);
                    break;
                case "submit":
                    makeBid(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown route: " + route);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Unexpected error: " + e.getMessage());
            req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
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
			req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
		} catch (NumberFormatException e) {
			req.setAttribute("messageType", "error");
			req.setAttribute("message", "Invalid Lot ID provided.");
			req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
		}
	}

    private void showProductDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idProductParam = req.getParameter("idProduct");
            if (idProductParam == null || idProductParam.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
                return;
            }

            int idProduct = Integer.parseInt(idProductParam);
            ProductService productService = new ProductService();
            Product product = productService.findProductById(idProduct);

            if (product == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            // Obtener idLot del producto relacionado y enviarlo al JSP
            int idLot = product.getLot().getIdLot(); // Asegúrate de que la relación Producto-Lote está definida

            double currentPrice = product.getBid().isEmpty() ? product.getPriceInitial() :
                product.getBid().stream().mapToDouble(Bid::getCurrentPrice).max().orElse(product.getPriceInitial());

            // Establecer atributos para el JSP
            req.setAttribute("currentPrice", currentPrice);
            req.setAttribute("product", product);
            req.setAttribute("idLot", idLot); // Pasar idLot al JSP

            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Product ID");
        } catch (Exception e) {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Error retrieving product: " + e.getMessage());
            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
        }
    }


    private void makeBid(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            System.out.println("Route: " + req.getParameter("route"));
            System.out.println("Product ID: " + req.getParameter("idProduct"));
            System.out.println("Your Bid: " + req.getParameter("yourBid"));

            Bid newBid = parseBidFromRequest(req);
            BidJPA bidJPA = new BidJPA();

            if (bidJPA.createBid(newBid)) {
                req.setAttribute("messageType", "success");
                req.setAttribute("message", "Your bid has been successfully submitted.");
            } else {
                req.setAttribute("messageType", "error");
                req.setAttribute("message", "Failed to submit your bid.");
            }

            req.setAttribute("product", newBid.getProduct());
            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "An error occurred: " + e.getMessage());
            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
        }
    }

    private Bid parseBidFromRequest(HttpServletRequest req) throws IllegalArgumentException {
        int idProduct = Integer.parseInt(req.getParameter("idProduct"));
        HttpSession session = req.getSession();
        Bidder bidder = (Bidder) session.getAttribute("user");

        if (bidder == null) {
            throw new IllegalStateException("No bidder found in session.");
        }

        ProductService productService = new ProductService();
        Product product = productService.findProductById(idProduct);

        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + idProduct + " not found.");
        }

        double price = Double.parseDouble(req.getParameter("yourBid"));
        double currentPrice = product.getBid().isEmpty() 
            ? product.getPriceInitial() 
            : product.getBid().stream().mapToDouble(Bid::getCurrentPrice).max().orElse(product.getPriceInitial());

        if (price <= currentPrice) {
            throw new IllegalArgumentException("The bid price must be higher than the current price.");
        }

        Bid bid = new Bid();
        bid.setProduct(product);
        bid.setPrice(price);
        bid.setCurrentPrice(price);
        bid.setDateBid(new java.util.Date());
        bid.setState("Pending");

        return bid;
    }

    private void showBidsForProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int idProduct = Integer.parseInt(req.getParameter("idProduct"));

            ProductService productService = new ProductService();
            Product product = productService.findProductById(idProduct);

            if (product == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            req.setAttribute("product", product);
            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Unexpected error: " + e.getMessage());
            req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
        }
    }
}
