package controllers;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            default:
                throw new IllegalArgumentException("Unknown route: " + route);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Bidder bidder = (Bidder) session.getAttribute("user");

            int idLot = Integer.parseInt(req.getParameter("idLot"));
            req.setAttribute("idLot", idLot);

            ProductService productService = new ProductService();
            BidJPA bidJPA = new BidJPA();

            List<Product> products = productService.findProductsByLotId(idLot);

            req.setAttribute("products", products);
            req.getRequestDispatcher("jsp/BIDDER_LOT.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            req.setAttribute("messageType", "error");
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

            int idProduct = Integer.parseInt(idProductParam);
            int idLot = Integer.parseInt(req.getParameter("idLot"));
            req.setAttribute("idLot", idLot);

            ProductService productService = new ProductService();
            Product product = productService.findProductById(idProduct);

            BidJPA bidJPA = new BidJPA();
            List<Bid> bids = bidJPA.findBidByProductId(idProduct);

            req.setAttribute("product", product);
            req.setAttribute("bidCount", bids.size());
            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Product ID format.");
        } catch (Exception e) {
            throw new ServletException("Error retrieving product details", e);
        }
    }



    private void placeBid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Bidder bidder = (Bidder) session.getAttribute("user");

        try {
            String idProductParam = req.getParameter("idProduct");
            String bidAmountParam = req.getParameter("bidAmount");
            int idLot = Integer.parseInt(req.getParameter("idLot"));
            req.setAttribute("idLot", idLot);

            int idProduct = Integer.parseInt(idProductParam);
            double bidAmount = Double.parseDouble(bidAmountParam);


            ProductService productService = new ProductService();
            Product product = productService.findProductById(idProduct);

            BidJPA bidJPA = new BidJPA();
            List<Bid> bids = bidJPA.findBidByProductId(idProduct);

            if (bidAmount <= product.getPriceCurrent()) {
                req.setAttribute("messageType", "error");
                req.setAttribute("message", "Your bid must be higher than the current price.");
                req.setAttribute("bidCount", bids.size());
                req.setAttribute("product", product);
                req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
                return;
            }

            boolean success = createAndSaveBid(bidAmount, product, bidder);

            prepareResponse(req, resp, success, bidAmount, product, idProduct);
        } catch (NumberFormatException e) {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Invalid product ID or bid amount format.");
            req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error placing bid", e);
        }
    }

    private boolean createAndSaveBid(double bidAmount, Product product, Bidder bidder) {
        BidJPA bidJPA = new BidJPA();
        Bid newBid = new Bid(0, new Date(), bidAmount, Bid.BidState.ACTIVE);
        newBid.setProduct(product);
        newBid.setBidder(bidder);
        return bidJPA.createBid(newBid);
    }

    private void prepareResponse(HttpServletRequest req, HttpServletResponse resp, boolean success, double bidAmount,
            Product product, int idProduct) throws ServletException, IOException {
    	BidJPA bidJPA = new BidJPA();
        List<Bid> bids = bidJPA.findBidByProductId(idProduct);
        req.setAttribute("bidCount", new BidJPA().findBidByProductId(idProduct).size());
        req.setAttribute("product", product);

        if (success) {
            req.setAttribute("messageType", "success");
            req.setAttribute("message", "Your bid was successfully placed!");
        } else {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Failed to place your bid. Please try again.");
        }

        req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
    }
}
