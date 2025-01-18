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
import model.entities.Bid;
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
		// Control logic
		String route = (req.getParameter("route") == null) ? "list" : req.getParameter("route");

		switch (route) {
			case "list":
			try {
				this.list(req, resp);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
			case "productDetails":
				this.productDetails(req, resp);
				break;
			case "bidInfo":
	            this.bidInfo(req, resp);
	            break;
	        case "placeBid":
	            this.placeBid(req, resp);
	            break;
			case "edit":
				
				break;
			case "saveExisting":
				
				break;
			case "delete":
				
				break;
			case "accept":
				
				break;
			default:
				throw new IllegalArgumentException("Unknown route: " + route);
		}
	}

	private void list(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
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

	private void productDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Obtener el ID del producto
			String idProductParam = req.getParameter("idProduct");
			if (idProductParam == null || idProductParam.isEmpty()) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required.");
				return;
			}

			int idProduct = Integer.parseInt(idProductParam);

			// Buscar el producto por ID
			ProductService productService = new ProductService();
			Product product = productService.findProductById(idProduct);

			if (product == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
				return;
			}

			// Pasar los detalles del producto a la solicitud
			req.setAttribute("product", product);

			// Redirigir al JSP de detalles del producto
			req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Product ID format.");
		} catch (Exception e) {
			throw new ServletException("Error retrieving product details", e);
		}
	}
	
	private void bidInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try {
	        // Obtener el ID del producto desde los parámetros de la solicitud
	        String idProductParam = req.getParameter("idProduct");
	        if (idProductParam == null || idProductParam.isEmpty()) {
	            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required.");
	            return;
	        }

	        int idProduct = Integer.parseInt(idProductParam);

	        // Recuperar las pujas y el producto
	        ProductService productService = new ProductService();
	        BidJPA bidJPA = new BidJPA();

	        // Recuperar la información del producto
	        Product product = productService.findProductById(idProduct);
	        if (product == null) {
	            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
	            return;
	        }

	        // Obtener las pujas asociadas al producto
	        List<Bid> bids = bidJPA.findBidByProductId(idProduct);
	        Bid latestBid = bids.isEmpty() ? null : bids.get(bids.size() - 1); // Última puja, si existe

	        // Determinar el precio actual
	        double currentPrice = (latestBid != null) ? latestBid.getCurrentPrice() : product.getPriceInitial();

	        // Establecer atributos para la vista
	        req.setAttribute("product", product);
	        req.setAttribute("bids", bids);
	        req.setAttribute("latestBid", latestBid); // Puede ser null si no hay pujas
	        req.setAttribute("bidCount", bids.size());
	        req.setAttribute("currentPrice", currentPrice);

	        // Redirigir al JSP
	        req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
	    } catch (NumberFormatException e) {
	        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Product ID format.");
	    } catch (Exception e) {
	        throw new ServletException("Error retrieving bid information", e);
	    }
	}



	
	private void placeBid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try {
	        String idProductParam = req.getParameter("idProduct");
	        String bidAmountParam = req.getParameter("bidAmount");

	        if (idProductParam == null || bidAmountParam == null) {
	            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID and bid amount are required.");
	            return;
	        }

	        int idProduct = Integer.parseInt(idProductParam);
	        double bidAmount = Double.parseDouble(bidAmountParam);

	        BidJPA bidJPA = new BidJPA();
	        ProductService productService = new ProductService();

	        // Obtener el producto
	        Product product = productService.findProductById(idProduct);
	        if (product == null) {
	            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
	            return;
	        }

	        // Obtener las pujas del producto
	        List<Bid> bids = bidJPA.findBidByProductId(idProduct);
	        double currentPrice = bids.isEmpty() ? product.getPriceInitial() : bids.get(bids.size() - 1).getCurrentPrice();

	        // Validar el monto de la puja
	        if (bidAmount <= currentPrice) {
	            req.setAttribute("message", "Your bid must be higher than the current price.");
	            req.setAttribute("messageType", "error");
	            req.setAttribute("currentPrice", currentPrice);
	            req.setAttribute("bidCount", bids.size());
	            req.setAttribute("product", product);
	            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
	            return;
	        }

	        // Expirar las demás pujas activas
	        for (Bid bid : bids) {
	            if ("ACTIVE".equals(bid.getState())) {
	                bid.setState("EXPIRED");
	                bidJPA.updateBid(bid);
	            }
	        }

	        // Crear la nueva puja
	        Bid newBid = new Bid();
	        newBid.setProduct(product);
	        newBid.setBid(bidAmount);
	        newBid.setCurrentPrice(bidAmount);
	        newBid.setState("ACTIVE");
	        newBid.setDateBid(new java.util.Date());

	        boolean success = bidJPA.createBid(newBid);

	        req.setAttribute("currentPrice", bidAmount);
	        req.setAttribute("bidCount", bids.size() + 1); // Incrementa el contador de pujas
	        req.setAttribute("product", product);

	        if (success) {
	            req.setAttribute("message", "Your bid was successfully placed!");
	            req.setAttribute("messageType", "success");
	        } else {
	            req.setAttribute("message", "Failed to place your bid. Please try again.");
	            req.setAttribute("messageType", "error");
	        }

	        req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
	    } catch (Exception e) {
	        throw new ServletException("Error placing bid", e);
	    }
	}

}
