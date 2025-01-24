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
import model.entities.Auctioneer;
import model.entities.Bid;
import model.entities.Bidder;
import model.entities.Product;
import model.entities.User;
import model.jpa.BidJPA;
import model.service.ProductService;
import model.service.UserService;

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

        switch (route) {
            case "list":
            	this.list(req, resp);
                break;
            case "productDetails":
                this.viewproductDetails(req, resp);
                break;
            case "placeBid":
                this.placeBid(req, resp);
                break;
            case "history":
                break;
            default:
                throw new IllegalArgumentException("Unknown route: " + route);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			Bidder bidder = (Bidder) session.getAttribute("user");
			if (bidder == null) {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
				return;
			}

			int idLot = Integer.parseInt(req.getParameter("idLot"));
			req.setAttribute("idLot", idLot); // Asegurar que el JSP reciba el idLot
			ProductService productService = new ProductService();
			List<Product> products = productService.findProductsByLotId(idLot);

			req.setAttribute("products", products);
			req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("messageType", "error");
			req.setAttribute("message", "Unexpected error: " + e.getMessage());
			req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
		}
	}

    private void viewproductDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idProductParam = req.getParameter("idProduct");
            if (idProductParam == null || idProductParam.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required.");
                return;
            }

            int idProduct = Integer.parseInt(idProductParam);

            ProductService productService = new ProductService();
            Product product = productService.findProductById(idProduct);

            if (product == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
                return;
            }

            req.setAttribute("product", product);
            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Product ID format.");
        } catch (Exception e) {
            throw new ServletException("Error retrieving product details", e);
        }
    }


    private void placeBid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");

        // Validación del usuario en sesión
        if (user == null || !(user instanceof Bidder)) {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "You must be logged in as a bidder to place a bid.");
            req.getRequestDispatcher("jsp/LOGIN.jsp").forward(req, resp);
            return;
        }

        Bidder bidder = (Bidder) user;

        try {
            // Recuperar parámetros de la solicitud
            String idProductParam = req.getParameter("idProduct");
            String bidAmountParam = req.getParameter("bidAmount");

            if (idProductParam == null || bidAmountParam == null) {
                req.setAttribute("messageType", "error");
                req.setAttribute("message", "Product ID and bid amount are required.");
                req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
                return;
            }

            int idProduct = Integer.parseInt(idProductParam);
            double bidAmount = Double.parseDouble(bidAmountParam);

            BidJPA bidJPA = new BidJPA();
            ProductService productService = new ProductService();

            // Validar producto
            Product product = productService.findProductById(idProduct);
            if (product == null) {
                req.setAttribute("messageType", "error");
                req.setAttribute("message", "Product not found.");
                req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
                return;
            }

            // Validar monto de la puja
            List<Bid> bids = bidJPA.findBidByProductId(idProduct);
            double currentPrice = bids.isEmpty() ? product.getPriceInitial() : bids.get(bids.size() - 1).getCurrentPrice();

            if (bidAmount <= currentPrice) {
                req.setAttribute("messageType", "error");
                req.setAttribute("message", "Your bid must be higher than the current price.");
                req.setAttribute("currentPrice", currentPrice);
                req.setAttribute("bidCount", bids.size());
                req.setAttribute("product", product);
                req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
                return;
            }

            // Expirar pujas activas
            for (Bid bid : bids) {
                if (Bid.BidState.ACTIVE.equals(bid.getState())) {
                    bid.setState(Bid.BidState.LOST);
                    bidJPA.updateBid(bid);
                }
            }

            // Crear nueva puja
            Bid newBid = new Bid();
            newBid.setProduct(product);
            newBid.setBid(bidAmount);
            newBid.setCurrentPrice(bidAmount);
            newBid.setState(Bid.BidState.ACTIVE);
            newBid.setDateBid(new java.util.Date());
            newBid.setUser(bidder);

            boolean success = bidJPA.createBid(newBid);

            // Actualizar valores para la vista
            req.setAttribute("currentPrice", bidAmount);
            req.setAttribute("bidCount", bids.size() + 1);
            req.setAttribute("product", product);

            if (success) {
                req.setAttribute("messageType", "success");
                req.setAttribute("message", "Your bid was successfully placed!");
            } else {
                req.setAttribute("messageType", "error");
                req.setAttribute("message", "Failed to place your bid. Please try again.");
            }

            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Invalid product ID or bid amount format.");
            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error placing bid", e);
        }
    }


}
