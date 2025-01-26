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
import model.entities.*;
import model.jpa.BidJPA;
import model.service.BidService;
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
            case "placebid":
                this.placeBid(req, resp);
                break;
            case "confirm":
                this.confirmBid(req, resp);
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
            String bidAmountParam = req.getParameter("bidAmount");

            if (bidAmountParam != null) {
               double bidAmount = Double.parseDouble(bidAmountParam);
               req.setAttribute("bidAmount", bidAmount);
            }

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
        double bidAmount = Double.parseDouble(req.getParameter("bidAmount"));
        int idProduct = Integer.parseInt(req.getParameter("idProduct"));
        int idLot = Integer.parseInt(req.getParameter("idLot"));

        System.out.println("idProduct: " + idProduct);
        System.out.println("idLot: " + idLot);
        System.out.println("bidAmount: " + bidAmount);

        req.setAttribute("routemodal", "confirm");
        req.setAttribute("idProduct", idProduct);
        req.setAttribute("idLot", idLot);
        req.setAttribute("bidAmount", bidAmount);
        req.getRequestDispatcher("PlaceBidController?route=productDetails").forward(req, resp);

        //req.getRequestDispatcher("jsp/PRODUCT.jsp").forward(req, resp);
    }

    private void confirmBid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Estoy en confirmBid");
        Bid bid = parseBidFromRequest(req);
        BidService bidService = new BidService();
        if(bidService.createBid(bid)) {
            req.setAttribute("messageType", "info");
            req.setAttribute("message", "Bid placed successfully.");
            req.getRequestDispatcher("PlaceBidController?route=productDetails").forward(req, resp);
        } else {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Failed to place bid.");
            req.getRequestDispatcher("PlaceBidController?route=productDetails").forward(req, resp);
        }
    }


    private Bid parseBidFromRequest(HttpServletRequest req) {

        HttpSession session = req.getSession();
        Bidder auctioneer = (Bidder) session.getAttribute("user");
        double bidAmount = Double.parseDouble(req.getParameter("bidAmount"));
        int idProduct = Integer.parseInt(req.getParameter("idProduct"));
        Product product = new ProductService().findProductById(idProduct);
        return new Bid(0,new Date(),bidAmount, product, auctioneer);
    }
}